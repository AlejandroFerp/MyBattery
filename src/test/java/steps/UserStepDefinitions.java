package steps;

import com.batteryworkshop.models.User;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserStepDefinitions {
    private User usuario;
    private boolean accesoPermitido;
    private boolean autenticado;

    @Dado("un usuario con nombre {string} y contraseña {string}")
    public void un_usuario_con_nombre_y_contraseña(String nombre, String contraseña) {
        usuario = new User(nombre, contraseña);
    }

    @Dado("un usuario con nombre {string}, contraseña {string} y tipo {string}")
    public void un_usuario_con_nombre_contraseña_y_tipo(String nombre, String contraseña, String tipo) {
        usuario = new User(nombre, contraseña, tipo);
    }

    @Cuando("inicia sesión con nombre {string} y contraseña {string}")
    public void inicia_sesion_con_nombre_y_contraseña(String nombre, String contraseña) {
        autenticado = usuario.login(nombre, contraseña);
    }

    @Cuando("intenta acceder a un método restringido")
    public void intenta_acceder_a_un_metodo_restringido() {
        accesoPermitido = usuario.hasPermission("RESTRICTED");
    }

    @Entonces("el inicio de sesión es exitoso")
    public void el_inicio_de_sesion_es_exitoso() {
        assertTrue(autenticado, "El usuario debería autenticarse correctamente");
    }

    @Entonces("el inicio de sesión falla")
    public void el_inicio_de_sesion_falla() {
        assertFalse(autenticado, "El usuario no debería autenticarse");
    }

    @Entonces("se permite el acceso")
    public void se_permite_el_acceso() {
        assertTrue(accesoPermitido, "El acceso debería estar permitido");
    }

    @Entonces("se deniega el acceso")
    public void se_deniega_el_acceso() {
        assertFalse(accesoPermitido, "El acceso debería estar denegado");
    }
}
