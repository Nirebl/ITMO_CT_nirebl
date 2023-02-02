package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IniFile {
    private Pattern keyValue = Pattern.compile("\\s*([^=]*)=(.*)");
    private Map<String, String> entries = new HashMap<>();

    public IniFile() {
    }

    public void load(String iniFileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(iniFileName))) {
            String line;
            String section = null;
            while ((line = br.readLine()) != null) {
                Matcher m = keyValue.matcher(line);
                if (m.matches()) {
                    String key = m.group(1).trim();
                    String value = m.group(2).trim();

                    entries.put(key, value);
                }
            }
        }
    }

    public String getString(String key, String defaultvalue) {
        String val = entries.get(key);
        if (val == null) {
            return defaultvalue;
        }

        return val;
    }

    public int getInt(String key, int defaultvalue) {
        String val = entries.get(key);
        if (val == null) {
            return defaultvalue;
        }

        return Integer.parseInt(val);
    }

}
