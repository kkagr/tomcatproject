package javax.kkagr.servlet;

import java.io.PrintWriter;

public interface ServletResopnse {
    void setWriter(PrintWriter out);
    PrintWriter getWriter();
}
