
package com.focus3d.pano.velocity.directive;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

/**
 * 判断不为空的指令
 * 使用方法：#ifnotnull()
 * *
 * @author lihaijun
 *
 */
public class Ifnotnull extends Directive {

    @Override
    public String getName() {
        return "ifnotnull";
    }

    @Override
    public int getType() {
        return BLOCK;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException,
            ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        Object value = node.jjtGetChild(0).value(context);
        if (value != null) {
            Node content = node.jjtGetChild(1);
            content.render(context, writer);
        }
        return true;
    }
}
