package me.deadlight.ezchestshop.databases.persistence;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class DatabaseShopObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private String owner;
    private String item;
    private double buyPrice;
    private double sellPrice;
    private boolean messageToggle;
    private boolean disableBuyOption;
    private boolean disableSellOption;
    @ElementCollection
    private List<String> admins; // UUIDs of admins
    private boolean shareIncomeOption;
    private boolean adminShopStatus;
    private String rotation;
    @ElementCollection
    @Column(length = 1024)
    private List<String> customMessages; // Ensure max size 4 in application logic

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public boolean isMessageToggle() {
        return messageToggle;
    }

    public void setMessageToggle(boolean messageToggle) {
        this.messageToggle = messageToggle;
    }

    public boolean isDisableBuyOption() {
        return disableBuyOption;
    }

    public void setDisableBuyOption(boolean disableBuyOption) {
        this.disableBuyOption = disableBuyOption;
    }

    public boolean isDisableSellOption() {
        return disableSellOption;
    }

    public void setDisableSellOption(boolean disableSellOption) {
        this.disableSellOption = disableSellOption;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    public boolean isShareIncomeOption() {
        return shareIncomeOption;
    }

    public void setShareIncomeOption(boolean shareIncomeOption) {
        this.shareIncomeOption = shareIncomeOption;
    }

    public boolean isAdminShopStatus() {
        return adminShopStatus;
    }

    public void setAdminShopStatus(boolean adminShopStatus) {
        this.adminShopStatus = adminShopStatus;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public List<String> getCustomMessages() {
        return customMessages;
    }

    public void setCustomMessages(List<String> customMessages) {
        this.customMessages = customMessages;
    }
}
