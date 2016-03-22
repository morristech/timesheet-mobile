package com.samsistemas.timesheet.domain;

import com.google.gson.annotations.SerializedName;
import com.orm.dsl.NotNull;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import org.parceler.Parcel;

/**
 * Pojo class representing a Client
 *
 * @author jonatan.salas
 */
@Parcel
@Table
public class Client {

    @NotNull
    @Unique
    private transient Long id;

    /**
     * The id of the client stored in the server
     */
    @NotNull
    @Unique
    @SerializedName("id")
    private Long serverId;

    /**
     * The name of the client
     */
    @NotNull
    @Unique
    @SerializedName("name")
    private String name;

    /**
     * The short name of the client
     */
    @NotNull
    @Unique
    @SerializedName("short_name")
    private String shortName;

    /**
     * Flag that indicates if the client is still active
     */
    @NotNull
    @SerializedName("enabled")
    private Boolean enabled;

    /**
     * Public Constructor
     */
    public Client() { }

    public Long getId() {
        return id;
    }

    public Client setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getServerId() {
        return serverId;
    }

    public Client setServerId(Long serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Client setName(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public Client setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Client setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
