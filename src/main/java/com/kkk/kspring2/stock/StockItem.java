package com.kkk.kspring2.stock;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockItem implements Serializable {
    String code;
    String name;
    String type;
}
