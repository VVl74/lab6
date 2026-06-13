package com.example.managers;

import com.example.collection.SpaceMarine;
import com.example.utils.Parser;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DBCollectionManager {
    private DBManager dbManager;
    private Parser parser;
    private LocalDateTime timeinit;

    public DBCollectionManager(DBManager ndbManager) {
        dbManager = ndbManager;
        parser = new Parser();
        timeinit = LocalDateTime.now();
    }

    public LocalDateTime getTimeinit() {
        return timeinit;
    }

    public boolean registerUser(String username, String passwordHash) {
        String zapr = "INSERT INTO users (login, password_hash) VALUES (?, ?)";

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
        String zapr = "SELECT id FROM users WHERE login = ? AND password_hash = ?";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setString(1, username);
            przapr.setString(2, passwordHash);
            ResultSet rs = przapr.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            return false;
        }
    }

    public HashMap<Integer, SpaceMarine> getCollection() {
        String zapr = "SELECT * FROM space_marines";

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

    public boolean insertMarine(SpaceMarine marine, int ownerId) {
        String zapr = "INSERT INTO space_marines (" +
                "name, X, Y, datetime, health, category, " +
                "weapontype, meleeweapon, chapter_name, chapter_parent_legion, " +
                "chapter_marines_count, chapter_world, owner_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setString(1, marine.getName());
            przapr.setInt(2, (int) marine.getCoordinates().getX());
            przapr.setLong(3, marine.getCoordinates().getY());
            przapr.setTimestamp(4, Timestamp.valueOf(marine.spaceGetTime()));
            przapr.setDouble(5, marine.getHealth());
            przapr.setString(6, marine.getCategory().toString());
            przapr.setString(7, marine.getWeaponType().toString());
            przapr.setString(8, marine.getMeleeWeapon().toString());
            przapr.setString(9, marine.getChapter().getName());
            przapr.setString(10, marine.getChapter().getParentLegion());
            przapr.setLong(11, marine.getChapter().getMarinesCount());
            przapr.setString(12, marine.getChapter().getWorld());
            przapr.setInt(13, ownerId);
            przapr.executeUpdate();

            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean removeMarine(int id, int ownerId) {
        String zapr = "DELETE FROM space_marines WHERE id = ? AND owner_id = ?";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setInt(1, id);
            przapr.setInt(2, ownerId);
            int del = przapr.executeUpdate();

            return del > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public int getUserId(String name) {
        String zapr = "SELECT id FROM users WHERE login = ?";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setString(1, name);

            ResultSet rs = przapr.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }

        } catch (SQLException e) {
            return -1;
        }
    }

    public int removeLowerKeyMarine(int id, int ownerId) {
        String zapr = "DELETE FROM space_marines WHERE id < ? AND owner_id = ?";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setInt(1, id);
            przapr.setInt(2, ownerId);
            int del = przapr.executeUpdate();

            return del;

        } catch (SQLException e) {
            return 0;
        }
    }

    public int removeAll(int ownerId) {
        String zapr = "DELETE FROM space_marines WHERE owner_id = ?";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setInt(1, ownerId);

            int del = przapr.executeUpdate();

            return del;

        } catch (SQLException e) {
            return 0;
        }
    }

    public HashMap<Integer, SpaceMarine> selectChapterLess(int count) {
        String zapr = "SELECT * FROM space_marines WHERE chapter_marines_count < ?";

        HashMap<Integer, SpaceMarine> collection = new HashMap<>();

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setInt(1, count);

            ResultSet rs = przapr.executeQuery();

            while(rs.next()) {
                SpaceMarine marine = parser.parseSQLMarine(rs);
                collection.put(marine.getId(), marine);
            }


            return collection;

        } catch (SQLException e) {
            return collection;
        }
    }

    public boolean updateMarine(int id, SpaceMarine marine, int ownerId) throws SQLException {
        String sql = "UPDATE space_marines SET " +
                "name = ?, X = ?, Y = ?, datetime = ?, health = ?, category = ?, " +
                "weapontype = ?, meleeweapon = ?, chapter_name = ?, chapter_parent_legion = ?, " +
                "chapter_marines_count = ?, chapter_world = ? " +
                "WHERE id = ? AND owner_id = ?";
        try (PreparedStatement ps = dbManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, marine.getName());
            ps.setInt(2, (int) marine.getCoordinates().getX());
            ps.setLong(3, marine.getCoordinates().getY());
            ps.setTimestamp(4, Timestamp.valueOf(marine.spaceGetTime()));
            ps.setDouble(5, marine.getHealth());
            ps.setString(6, marine.getCategory().toString());
            String weaponStr = marine.getWeaponType() == null ? null : marine.getWeaponType().toString();
            ps.setString(7, weaponStr);
            ps.setString(8, marine.getMeleeWeapon().toString());
            ps.setString(9, marine.getChapter().getName());
            ps.setString(10, marine.getChapter().getParentLegion());
            ps.setLong(11, marine.getChapter().getMarinesCount());
            ps.setString(12, marine.getChapter().getWorld());
            ps.setInt(13, id);
            ps.setInt(14, ownerId);

            return ps.executeUpdate() > 0;
        }
    }

    public int countLessHealth(double health) {
        String zapr = "SELECT COUNT(*) FROM space_marines WHERE health < ?";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setDouble(1, health);

            ResultSet del = przapr.executeQuery();

            if (del.next()) {
                return del.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            return 0;
        }
    }

    public int countElement() {
        String zapr = "SELECT COUNT(*) FROM space_marines";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            ResultSet del = przapr.executeQuery();

            if (del.next()) {
                return del.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            return 0;
        }
    }

    public HashMap<Integer, SpaceMarine> selectChapterGreat(int count) {
        String zapr = "SELECT * FROM space_marines WHERE chapter_marines_count > ?";

        HashMap<Integer, SpaceMarine> collection = new  HashMap<>();

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setInt(1, count);

            ResultSet rs = przapr.executeQuery();

            while(rs.next()) {
                SpaceMarine marine = parser.parseSQLMarine(rs);
                collection.put(marine.getId(), marine);
            }


            return collection;

        } catch (SQLException e) {
            return collection;
        }
    }

    public boolean updateLessHelth(int id, SpaceMarine marine, int ownerId) throws SQLException {
        String sql = "UPDATE space_marines SET " +
                "name = ?, X = ?, Y = ?, datetime = ?, health = ?, category = ?, " +
                "weapontype = ?, meleeweapon = ?, chapter_name = ?, chapter_parent_legion = ?, " +
                "chapter_marines_count = ?, chapter_world = ? " +
                "WHERE id = ? AND owner_id = ? AND health > ?";
        try (PreparedStatement ps = dbManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, marine.getName());
            ps.setInt(2, (int) marine.getCoordinates().getX());
            ps.setLong(3, marine.getCoordinates().getY());
            ps.setTimestamp(4, Timestamp.valueOf(marine.spaceGetTime()));
            ps.setDouble(5, marine.getHealth());
            ps.setString(6, marine.getCategory().toString());
            String weaponStr = marine.getWeaponType() == null ? null : marine.getWeaponType().toString();
            ps.setString(7, weaponStr);
            ps.setString(8, marine.getMeleeWeapon().toString());
            ps.setString(9, marine.getChapter().getName());
            ps.setString(10, marine.getChapter().getParentLegion());
            ps.setLong(11, marine.getChapter().getMarinesCount());
            ps.setString(12, marine.getChapter().getWorld());
            ps.setInt(13, id);
            ps.setInt(14, ownerId);
            ps.setDouble(15, marine.getHealth());

            return ps.executeUpdate() > 0;
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

        String createSpace = "CREATE TABLE IF NOT EXISTS space_marines (" +
                "id SERIAL PRIMARY KEY NOT NULL, " +
                "name TEXT NOT NULL, " +
                "X INTEGER NOT NULL, " +
                "Y INTEGER NOT NULL, " +
                "datetime TIMESTAMP NOT NULL," +
                "health DOUBLE NOT NULL," +
                "category ENUM('ASSAULT', 'TACTICAL', 'HELIX') DEFAULT 'ASSAULT' NOT NULL," +
                "weapontype ENUM('BOLTGUN', 'MELTAGUN', 'FLAMER', 'HEAVY_FLAMER') NOT NULL," +
                "meleeweapon ENUM('CHAIN_SWORD', 'POWER_SWORD', 'CHAIN_AXE', 'MANREAPER', 'POWER_FIST') NOT NULL," +
                "chapter_name TEXT NOT NULL, " +
                "chapter_parent_legion TEXT NOT NULL," +
                "chapter_marines_count INTEGER NOT NULL CHECK (chapter_marines_count > 0 AND chapter_marines_count <= 1000), " +
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





    ArrayList<String> scriptPul = new ArrayList<String>();

    /**
     * Метод добавления скрипта
     */
    public void scriptInsert(String a) {
        scriptPul.add(a);
    }
    /**
     * Метод удаления элемента
     */
    public void scriptRemove(String a) {
        scriptPul.remove(a);
    }
    /**
     * Метод проверяющий содержится ли скрипт в текущем скриптпуле
     */
    public Boolean scriptIf(String a) {
        if (scriptPul.contains(a)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
