package org.example.skills.repository;

import org.example.skills.vo.Admin;

public interface APIRepository {

    Admin login(String name, String passwd);
}
