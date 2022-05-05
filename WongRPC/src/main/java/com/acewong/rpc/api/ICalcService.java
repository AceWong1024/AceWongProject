package com.acewong.rpc.api;

/**
 * @author AceWong
 */
public interface ICalcService {
    /**
     * @param a
     * @param b
     * @return
     */
    int add(int a, int b);

    /**
     * @param a
     * @param b
     * @return
     */
    int sub(int a, int b);

    /**
     * @param a
     * @param b
     * @return
     */
    int mult(int a, int b);

    /**
     * @param a
     * @param b
     * @return
     */
    int div(int a, int b);
}
