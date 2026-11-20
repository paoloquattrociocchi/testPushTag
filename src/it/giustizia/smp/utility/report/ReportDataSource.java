package it.giustizia.smp.utility.report;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportDataSource extends JRBeanCollectionDataSource implements JRDataSource {

    private final Iterator<?> data;
    private Object            obj;

    public ReportDataSource(final Collection<?> data) {

        super(data);
        this.data = data.iterator();
    }

    @Override
    public Object getFieldValue(final JRField field) throws JRException {

        Object value = null;
        final String fieldPath = field.getName();
        try {
            Object obj = this.obj;
            if (!"this".equals(fieldPath)) {
                for (final String f : fieldPath.split("\\.")) {
                    final String methodName = "get" + f.substring(0, 1).toUpperCase() + f.substring(1);
                    final Method method = obj.getClass().getMethod(methodName, (Class<?>[]) null);
                    obj = method.invoke(obj, (Object[]) null);
                }
            }
            value = obj;
        } catch (final Throwable e) {
            value = "Field Not Found";
        }
        return value;
    }

    @Override
    public boolean next() {

        final boolean hasNext = (data != null) && data.hasNext();
        if (hasNext) {
            obj = data.next();
        }
        return hasNext;
    }
}
