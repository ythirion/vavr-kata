package com.bil.katas.vavr.filtering;

import io.vavr.collection.Seq;

/**
 * @author Alexandre Grison (eqk83)
 */
public class SeqKata {
    Seq<Integer> filterEven(Seq<Integer> s) {
        return s.filter(e -> e % 2 == 0);
    }

}
