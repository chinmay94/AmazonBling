package com.amazon.mshopbling.AsinHelpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Asin {
    private String asinTitle;
    private String asin;
    private String imageUrl;
    private String category;
}
