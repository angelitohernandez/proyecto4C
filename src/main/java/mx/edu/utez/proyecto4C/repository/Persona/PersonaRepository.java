
package mx.edu.utez.proyecto4C.repository.Persona;

import mx.edu.utez.proyecto4C.model.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

}
