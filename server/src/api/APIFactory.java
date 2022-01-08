package api;

import api.auth.*;

import java.util.Arrays;
import java.util.List;

public class APIFactory {
    public static API getAPI(String path) {
        for(API api : apis) {
            if(api.getPath().compareTo(path) == 0) {
                return api;
            }
        }
        return new NotFound();
    }

    private static final List<API> apis = Arrays.asList(new LoginAPI(), new RegisterAPI(), new NotFound());
}
