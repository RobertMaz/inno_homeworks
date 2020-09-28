package app;

import beans.VarBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Bean for out all system environment.
 */
@Stateless
@Named
public class SystemReq {

    /**
     * Find environment by name.
     * @param key
     * @return
     * @throws JsonProcessingException
     */
    public String find(String key) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerFor(VarBean.class);
        String value = getMap().get(key);
        if (value == null) {
            return "NOT FIND VARIABLE";
        }
        return objectWriter.writeValueAsString(new VarBean(key, value));
    }

    /**
     * Return all system environment in String.
     * @return
     */
    public String findAll() {
        JSONArray jsonArray = new JSONArray();
        getMap().forEach((key, val) -> {
            jsonArray.put(new JSONObject(new VarBean(key, val)));
        });
        return jsonArray.toString();
    }

    /**
     * Return all system environment.
     * @return
     */
    public Map<String, String> getMap() {
        return System.getenv();
    }

    /**
     * Return all system environment as List VarBeans.
     * For JSF page.
     * @return
     */
    public List<VarBean> getMapAsList() {
        return getMap().entrySet().stream()
                .map((k) -> new VarBean(k.getKey(), k.getValue()))
                .collect(Collectors.toList());
    }

}
