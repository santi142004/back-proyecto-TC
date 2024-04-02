package com.BackendTc.TuCafe.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewRequest {

    private String comment;
    private int stars;
    private long client;
}
