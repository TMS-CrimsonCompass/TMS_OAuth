package tms.crimsoncompass.TMS_OAuth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tms.crimsoncompass.TMS_OAuth.service.UserSyncService;

@FeignClient(name = "tms-service", url = "${tms.service.url}")
public interface TmsUserClient {

    @PostMapping("/api/users/sync")
    void syncUser(@RequestBody UserSyncService.UserSyncRequest request);
}
