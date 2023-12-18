package com.khagani.to_do_app.dto;

import com.khagani.to_do_app.entity.Status;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

    private String name;
    private String description;
    private Status status;

}
