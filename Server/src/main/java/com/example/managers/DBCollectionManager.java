package com.example.managers;

import com.example.collection.SpaceMarine;
import com.example.utils.Parser;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DBCollectionManager {
    private DBManager dbManager;
    private Parser parser;

    public DBCollectionManager(DBManager ndbManager) {
        dbManager = ndbManager;
        parser = new Parser();
    }

    public boolean registerUser(String username, String passwordHash) {
        String zapr = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setString(1, username);
            przapr.setString(2, passwordHash);
            przapr.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean proverkUser(String username, String passwordHash) {
        String zapr = "SELECT ID FROM users WHERE login = ? AND password_hash = ?";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setString(1, username);
            przapr.setString(2, passwordHash);
            ResultSet rs = przapr.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            return false;
        }
    }

    public HashMap<Integer, SpaceMarine> getCollection(String username, String passwordHash) {
        String zapr = "SELECT * FROM users";

        HashMap<Integer, SpaceMarine> collection = new HashMap<>();

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            ResultSet rs = przapr.executeQuery();
            while(rs.next()) {
                SpaceMarine marine = parser.parseSQLMarine(rs);
                collection.put(marine.getId(), marine);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return collection;
    }

    public boolean insertMarine(String username, String passwordHash) {
        String zapr = "INSERT INTO space_marines (" +
                "name, coord_x, coord_y, creation_date, health, category, " +
                "weapon_type, melee_weapon, chapter_name, chapter_parent_legion, " +
                "chapter_marines_count, chapter_world, owner_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr, Statement.RETURN_GENERATED_KEYS)) {
            przapr.setString(1, username);
            przapr.setString(2, passwordHash);
            przapr.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }

    }

    public void InitTable() throws SQLException {
        String dropcat = "DROP TYPE IF EXISTS astartes_category";
        String dropweap = "DROP TYPE IF EXISTS weapon_type";
        String dropmelee = "DROP TYPE IF EXISTS melee_weapon_type";

        String category = "CREATE TYPE astartes_category AS ENUM ('ASSAULT', 'TACTICAL', 'HELIX')";
        String weapon = "CREATE TYPE weapon_type AS ENUM ('BOLTGUN', 'MELTAGUN', 'FLAMER', 'HEAVY_FLAMER')";
        String melee = "CREATE TYPE melee_weapon_type AS ENUM ('CHAIN_SWORD', 'POWER_SWORD', 'CHAIN_AXE', 'MANREAPER', 'POWER_FIST')";

        String createSpace = "CREATE TABLE IF NOT EXISTS spacemarines (" +
                "id SERIAL PRIMARY KEY NOT NULL, " +
                "name TEXT NOT NULL, " +
                "X INTEGER NOT NULL, " +
                "Y INTEGER NOT NULL, " +
                "datetime TIMESTAMP NOT NULL," +
                "health INTEGER NOT NULL," +
                "category ENUM('ASSAULT', 'TACTICAL', 'HELIX') DEFAULT 'ASSAULT' NOT NULL," +
                "weapontype ENUM('BOLTGUN', 'MELTAGUN', 'FLAMER', 'HEAVY_FLAMER') NOT NULL," +
                "meleeweapon ENUM('CHAIN_SWORD', 'POWER_SWORD', 'CHAIN_AXE', 'MANREAPER', 'POWER_FIST') NOT NULL," +
                "chapter_name TEXT NOT NULL, " +
                "chapter_parent_legion TEXT NOT NULL," +
                "chapter_marines_count BIGINT NOT NULL CHECK (chapter_marines_count > 0 AND chapter_marines_count <= 1000), " +
                "chapter_world TEXT NOT NULL, " +
                "owner_id INTEGER REFERENCES users(id)) ON DELETE CASCADE;";

        String createUsers = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +
                "login TEXT UNIQUE NOT NULL, " +
                "password_hash TEXT NOT NULL);";
        Statement statement = dbManager.getConnection().createStatement();

        statement.executeUpdate(dropcat);
        statement.executeUpdate(dropweap);
        statement.executeUpdate(dropmelee);

        statement.executeUpdate(category);
        statement.executeUpdate(weapon);
        statement.executeUpdate(melee);

        statement.executeUpdate(createSpace);
        statement.executeUpdate(createUsers);
    }
}
