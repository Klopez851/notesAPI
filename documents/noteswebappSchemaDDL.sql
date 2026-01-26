CREATE SCHEMA `noteswebapp` ;
--
CREATE TABLE user_table (
  user_id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
  username VARCHAR(50) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  email varchar(254) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  template_id INTEGER NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
;

CREATE TABLE note (
  note_id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
  title VARCHAR(255),
  text_content TEXT,
  pinned BOOLEAN NOT NULL,
  hidden BOOLEAN NOT NULL,
  cosmetics TEXT,
  view_only BOOLEAN NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  user_id INTEGER NOT NULL,
  deleted BOOLEAN,
  time_left_before_deletion TIMESTAMP,
  label_id INTEGER,
  color_id INTEGER
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
;

CREATE TABLE label (
  label_name VARCHAR(50) NOT NULL,
  user_id INTEGER NOT NULL UNIQUE,
  label_id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
;

CREATE TABLE notecolor (
  user_id INTEGER NOT NULL,
  color_hex VARCHAR(7) NOT NULL,
  color_id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
;

CREATE TABLE uitemplate (
  template_id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
  template_name VARCHAR(25) NOT NULL UNIQUE,
  template_details TEXT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
;

ALTER TABLE user_table ADD CONSTRAINT fk_user_table_templateID FOREIGN KEY (template_id) REFERENCES uitemplate(template_id) ON DELETE NO ACTION ON UPDATE CASCADE;

ALTER TABLE note ADD CONSTRAINT fk_note_userID FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE note ADD CONSTRAINT fk_note_labelID FOREIGN KEY (label_id) REFERENCES label(label_id) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE note ADD CONSTRAINT fk_note_colorID FOREIGN KEY (color_id) REFERENCES notecolor(color_id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE label ADD CONSTRAINT fk_label_userID FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE notecolor ADD CONSTRAINT fk_notecolor_userID FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE INDEX idx_user_table_ ON user_table (user_id);
CREATE INDEX idx_user_table_template_id ON user_table (template_id);
CREATE INDEX idx_user_table_email ON user_table (email);


CREATE INDEX idx_note_UserID ON Note (user_id);
CREATE INDEX idx_note_LabelName ON Note (label_id);

CREATE INDEX idx_Label_UserID ON label (user_id);

CREATE INDEX idx_notecolor_UserID ON notecolor (user_id);

CREATE INDEX idx_UITemplate_ ON uitemplate (template_id);
