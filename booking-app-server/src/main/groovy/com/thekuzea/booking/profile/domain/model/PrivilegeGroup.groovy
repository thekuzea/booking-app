package com.thekuzea.booking.profile.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "privilege_groups")
class PrivilegeGroup {

    @Id
    String id

    String groupName

    List<String> privileges

    boolean isDefaultGroup
}
