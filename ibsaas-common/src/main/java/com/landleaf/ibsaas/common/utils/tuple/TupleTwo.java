package com.landleaf.ibsaas.common.utils.tuple;

/**
 * 二元组
 *
 * @param <T1>
 * @param <T2>
 */
public class TupleTwo<T1, T2> {

    private T1 t1;
    private T2 t2;


    public TupleTwo(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }


    public T1 getT1() {
        return t1;
    }

    public void setT1(T1 t1) {
        this.t1 = t1;
    }

    public T2 getT2() {
        return t2;
    }

    public void setT2(T2 t2) {
        this.t2 = t2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TupleTwo<?, ?> tupleTwo = (TupleTwo<?, ?>) o;

        if (!t1.equals(tupleTwo.t1)) return false;
        return t2.equals(tupleTwo.t2);
    }

    @Override
    public int hashCode() {
        int result = t1.hashCode();
        result = 31 * result + t2.hashCode();
        return result;
    }
}
