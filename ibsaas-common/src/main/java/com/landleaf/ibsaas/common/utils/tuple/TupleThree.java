package com.landleaf.ibsaas.common.utils.tuple;

/**
 * 三元组
 *
 * @param <T1>
 * @param <T2>
 */
public class TupleThree<T1, T2, T3> {

    private T1 t1;
    private T2 t2;
    private T3 t3;

    public TupleThree(T1 t1, T2 t2, T3 t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
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

    public T3 getT3() {
        return t3;
    }

    public void setT3(T3 t3) {
        this.t3 = t3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TupleThree<?, ?, ?> that = (TupleThree<?, ?, ?>) o;

        if (!t1.equals(that.t1)) return false;
        if (!t2.equals(that.t2)) return false;
        return t3.equals(that.t3);
    }

    @Override
    public int hashCode() {
        int result = t1.hashCode();
        result = 31 * result + t2.hashCode();
        result = 31 * result + t3.hashCode();
        return result;
    }
}
