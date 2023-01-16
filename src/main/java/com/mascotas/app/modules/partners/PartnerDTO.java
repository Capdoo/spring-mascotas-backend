package com.mascotas.app.modules.partners;

public class PartnerDTO {

    private long id;
    private String registerDate;
    private long user_id;

    public PartnerDTO(long id, String registerDate, long user_id) {
        this.id = id;
        this.registerDate = registerDate;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
