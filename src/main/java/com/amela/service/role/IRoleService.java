package com.amela.service.role;

import com.amela.model.Role;
import com.amela.service.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String name);
}
