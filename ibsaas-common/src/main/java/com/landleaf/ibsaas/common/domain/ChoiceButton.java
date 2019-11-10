package com.landleaf.ibsaas.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Lokiy
 * @date 2019/5/28 17:42
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceButton implements Serializable {

    private String choiceKey;

    private String choiceValue;
}
