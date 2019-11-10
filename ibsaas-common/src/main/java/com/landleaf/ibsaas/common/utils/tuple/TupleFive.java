package com.landleaf.ibsaas.common.utils.tuple;

/**
 * 五元组
 *
 * @param <T1>
 * @param <T2>
 * @param <T3>
 * @param <T4>
 */
public class TupleFive<T1, T2, T3, T4, T5> {

    private T1 t1;
    private T2 t2;
    private T3 t3;
    private T4 t4;
    private T5 t5;

    public TupleFive(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
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

    public T4 getT4() {
        return t4;
    }

    public void setT4(T4 t4) {
        this.t4 = t4;
    }

    public T5 getT5() {
        return t5;
    }

    public void setT5(T5 t5) {
        this.t5 = t5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TupleFive<?, ?, ?, ?, ?> tupleFive = (TupleFive<?, ?, ?, ?, ?>) o;

        if (!t1.equals(tupleFive.t1)) return false;
        if (!t2.equals(tupleFive.t2)) return false;
        if (!t3.equals(tupleFive.t3)) return false;
        if (!t4.equals(tupleFive.t4)) return false;
        return t5.equals(tupleFive.t5);
    }

    @Override
    public int hashCode() {
        int result = t1.hashCode();
        result = 31 * result + t2.hashCode();
        result = 31 * result + t3.hashCode();
        result = 31 * result + t4.hashCode();
        result = 31 * result + t5.hashCode();
        return result;
    }
}
