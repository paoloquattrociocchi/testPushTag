package it.giustizia.smp.utility;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

public final class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {

    public void registerCustomEditors(PropertyEditorRegistry registry) {

        registry.registerCustomEditor(java.lang.String.class, new CustomStringEditor());
        // è stato aggiunto per quale motivo ?
        // registry.registerCustomEditor(java.util.Date.class, new
        // CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
        registry.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
}
