package ca.vgorcinschi.util;

import java.util.ArrayList;
import java.util.List;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

/**
 * bean based on the dozer library (but with JDK-8 support) for fast
 * properties copying from one object to another
 * @author vgorcinschi
 */
@Component
public class DozerMapper {

    private final DozerBeanMapper mapper;
    
    public DozerMapper() {
        List<String> mappingFiles = new ArrayList();
        mappingFiles.add("dozerJdk8Converters.xml");
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(mappingFiles);
    }
    
    public DozerBeanMapper dozer(){
        return mapper;
    }
    
}
