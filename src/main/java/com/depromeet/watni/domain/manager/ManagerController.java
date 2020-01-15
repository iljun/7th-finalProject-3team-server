package com.depromeet.watni.domain.manager;

import com.depromeet.watni.domain.manager.domain.Manager;
import com.depromeet.watni.domain.manager.dto.ManagerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManagerController {
	private final ManagerService managerService;
	
	@PostMapping("api/managers")
	public Manager registerManager(@RequestBody ManagerDto managerDto) {
		return managerService.registerManager(managerDto.getGroupId(), managerDto.getMemberId());
	}
	@DeleteMapping("api/managers")
	public void deleteManager(@RequestBody Long managerId) {
		managerService.deleteManager(managerId);
	}

}
