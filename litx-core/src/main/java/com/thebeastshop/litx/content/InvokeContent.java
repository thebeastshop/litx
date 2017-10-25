package com.thebeastshop.litx.content;

import com.thebeastshop.litx.definition.MethodDefinition;

/**
 * @author gongjun[jun.gong@thebeastshop.com]
 * @since 2017-09-11 18:12
 */
public interface InvokeContent {

    MethodDefinition getDefination();

    Object[] getArguments();

    Object getResult();

    void rollback();

}
