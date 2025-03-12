package com.kulushev.app.views;

import org.springframework.beans.factory.annotation.Value;

public interface UserFullNameProjection {
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
}
