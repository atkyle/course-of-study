package security31.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import security31.service.MyBatisClientDetailsService;

import java.util.Map;

@RestController
public class ClientsController {

    @Autowired
    public MyBatisClientDetailsService myBatisClientDetailsService;

    @PostMapping("oauth/client/insert")
    public Object insert(@RequestBody Map map){
        return myBatisClientDetailsService.insert(map);
    }

}

