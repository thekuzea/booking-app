package com.thekuzea.booking.profile.domain.model;

import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "privilege_groups")
public class PrivilegeGroup {

    @Id
    private String id;

    private String groupName;

    private List<String> privileges;

    private boolean isDefaultGroup;
}
