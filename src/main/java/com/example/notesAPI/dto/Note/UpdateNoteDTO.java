package com.example.notesAPI.dto.Note;

import com.example.notesAPI.dto.Label.LabelDTO;
import com.example.notesAPI.dto.noteColor.NoteColorDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNoteDTO {
    @Schema(name = "id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;

    @Schema(name = "title", example = "Sample Note Title", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String title;

    @Schema(name = "textContent", example = "<TBD>", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String textContent;

    @Schema(name = "label", example = "{\n" +
            "            \"labelName\": \"sample label\",\n" +
            "            \"labelID\": 1\n" +
            "        }", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LabelDTO label;

    @Schema(name = "noteColor", example = "{\n" +
            "            \"colorHEX\": \"#b5a2c8\",\n" +
            "            \"colorID\": 1\n" +
            "        }", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private NoteColorDTO noteColor;

    @Schema(name = "cosmetics", example = "<TBD>", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String cosmetics;

    @Schema(name = "pinned", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private boolean pinned;

    @Schema(name = "hidden", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private boolean hidden;

    @Schema(name = "viewOnly", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private boolean viewOnly = false; //will remove default value once i figure out how to share notes amongst users

    @Schema(name = "deleted", example = "2026-05-04T03:59:30", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean deleted;

    public UpdateNoteDTO(int id, String title, String content, LabelDTO label, NoteColorDTO noteColor, String cosmetics,
                         boolean pinned, boolean hidden, boolean deleted) {
        this.id = id;
        this.title = title;
        this.textContent = content;
        this.label = label;
        this.noteColor = noteColor;
        this.cosmetics = cosmetics;
        this.pinned = pinned;
        this.hidden = hidden;
        this.deleted = deleted;
    }

    @JsonIgnore
    public boolean isValid() {
        if (pinned && hidden) {
            return false;
        }

        if ((title.isEmpty() || title.isBlank())
                && getTextContent().isEmpty() || textContent.isBlank()) {
            return false;
        }

        if (id < 0) {
            return false;
        }

        return true;
    }
}
