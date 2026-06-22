package com.example.managers;

import com.example.collection.SpaceMarine;
import com.example.utils.Parser;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class DBCollectionManager {
    private DBManager dbManager;
    private Parser parser;
    private LocalDateTime timeinit;
    private AutentManager autentManager;

    private final ConcurrentHashMap<Integer, SpaceMarine> collection = new ConcurrentHashMap<>(); //  коллекция в оперативной память

    public DBCollectionManager(DBManager ndbManager) {
        dbManager = ndbManager;
        parser = new Parser();
        timeinit = LocalDateTime.now();
        autentManager = new AutentManager();
    }

    public LocalDateTime getTimeinit() {
        return timeinit;
    }


    /**
     * Метод для загрузки коллекции в оперативную память
     *
     */
    public synchronized void loadCollection() {
        String zapr = "SELECT * FROM space_marines";

        try (PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            ResultSet rs = przapr.executeQuery();
            collection.clear();
            while (rs.next()) {
                SpaceMarine marine = parser.parseSQLMarine(rs);
                collection.put(marine.getId(), marine);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод для регистрации нового пользователя в БД
     *
     */
    public synchronized boolean registerUser(String username, String password) {
        String salt = autentManager.newSalt();
        String passwordHash = autentManager.hashPassword(password, salt);
        String zapr = "INSERT INTO users (login, password_hash, salt) VALUES (?, ?, ?)";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setString(1, username);
            przapr.setString(2, passwordHash);
            przapr.setString(3, salt);
            przapr.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Метод для проверки корректности введенных данных от пользователя
     *
     */
    public synchronized boolean proverkUser(String username, String password) {
        String zapr = "SELECT salt, password_hash FROM users WHERE login = ?";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setString(1, username);
            ResultSet rs = przapr.executeQuery();
            if (rs.next()) {
                String salt = rs.getString("salt");
                String passwordStor = rs.getString("password_hash");
                String passwordHash = autentManager.hashPassword(password, salt);
                return passwordHash.equals(passwordStor);
            } else {
                return false;
            }

        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Команда возвращает коллекцию из памяти
     *
     */
    public ConcurrentHashMap<Integer, SpaceMarine> getCollection() {
        return collection;
    }

    /**
     * Метод для добавления новых десантников в БД
     *
     */
    public synchronized boolean insertMarine(SpaceMarine marine, int ownerId) {
        String zapr = "INSERT INTO space_marines (" +
                "name, X, Y, datetime, health, category, " +
                "weapontype, meleeweapon, chapter_name, chapter_parent_legion, " +
                "chapter_marines_count, chapter_world, owner_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

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

            ResultSet rs = przapr.executeQuery();
            if (rs.next()) {
                int newId = rs.getInt("id");
                marine.setId(newId);
                collection.put(newId, marine);
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении в БД: " + e.getMessage());
            return false;
        }
    }

    /**
     * Метод для удаления десантника из БД
     *
     */
    public synchronized boolean removeMarine(int id, int ownerId) {
        String zapr = "DELETE FROM space_marines WHERE id = ? AND owner_id = ?";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setInt(1, id);
            przapr.setInt(2, ownerId);
            int del = przapr.executeUpdate();

            if (del > 0) {
                collection.remove(id);
                return true;
            }
            return false;

        } catch (SQLException e) {
            return false;
        }
    }

    public synchronized int getUserId(String name) {
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

    public synchronized int removeLowerKeyMarine(int id, int ownerId) {
        String zapr = "DELETE FROM space_marines WHERE id < ? AND owner_id = ?";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setInt(1, id);
            przapr.setInt(2, ownerId);
            int del = przapr.executeUpdate();

            if (del > 0) {
                collection.values().removeIf(m -> m.getId() < id && m.getOwnerId() == ownerId); // удаляем из памяти то, что удалили из БД
            }

            return del;

        } catch (SQLException e) {
            return 0;
        }
    }

    public synchronized int removeAll(int ownerId) {
        String zapr = "DELETE FROM space_marines WHERE owner_id = ?";

        try(PreparedStatement przapr = dbManager.getConnection().prepareStatement(zapr)) {
            przapr.setInt(1, ownerId);

            int del = przapr.executeUpdate();

            if (del > 0) {
                collection.values().removeIf(m -> m.getOwnerId() == ownerId);
            }

            return del;

        } catch (SQLException e) {
            return 0;
        }
    }

    // Команда получения данных фильтрация по коллекции в памяти
    public ConcurrentHashMap<Integer, SpaceMarine> selectChapterLess(int count) {
        ConcurrentHashMap<Integer, SpaceMarine> res = new ConcurrentHashMap<>();

        for (SpaceMarine marine : collection.values()) {
            if (marine.getChapter().getMarinesCount() < count) {
                res.put(marine.getId(), marine);
            }
        }

        return res;
    }

    public synchronized boolean updateMarine(int id, SpaceMarine marine, int ownerId) throws SQLException {
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

            if (ps.executeUpdate() > 0) {
                marine.setId(id);
                collection.put(id, marine);   // память обновляем только после успешного обновления в БД
                return true;
            }
            return false;
        }
    }

    // Команда получения данных: подсчёт по коллекции в памяти
    public int countLessHealth(double health) {
        int count = 0;
        for (SpaceMarine marine : collection.values()) {
            if (marine.getHealth() < health) {
                count++;
            }
        }
        return count;
    }

    // Команда получения данных: количество элементов в памяти
    public int countElement() {
        return collection.size();
    }

    // Команда получения данных: фильтрация по коллекции в памяти
    public ConcurrentHashMap<Integer, SpaceMarine> selectChapterGreat(int count) {
        ConcurrentHashMap<Integer, SpaceMarine> res = new ConcurrentHashMap<>();

        for (SpaceMarine marine : collection.values()) {
            if (marine.getChapter().getMarinesCount() > count) {
                res.put(marine.getId(), marine);
            }
        }

        return res;
    }

    public synchronized boolean updateLessHelth(int id, SpaceMarine marine, int ownerId) throws SQLException {
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

            if (ps.executeUpdate() > 0) {
                marine.setId(id);
                collection.put(id, marine);
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }



    public synchronized void InitTable() throws SQLException {
        String category = "DO $$ BEGIN " +
                "CREATE TYPE astartes_category AS ENUM ('ASSAULT', 'TACTICAL', 'HELIX'); " +
                "EXCEPTION WHEN duplicate_object THEN null; END $$;";
        String weapon = "DO $$ BEGIN " +
                "CREATE TYPE weapon_type AS ENUM ('BOLTGUN', 'MELTAGUN', 'FLAMER', 'HEAVY_FLAMER'); " +
                "EXCEPTION WHEN duplicate_object THEN null; END $$;";
        String melee = "DO $$ BEGIN " +
                "CREATE TYPE melee_weapon_type AS ENUM ('CHAIN_SWORD', 'POWER_SWORD', 'CHAIN_AXE', 'MANREAPER', 'POWER_FIST'); " +
                "EXCEPTION WHEN duplicate_object THEN null; END $$;";

        String createUsers = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +
                "login TEXT UNIQUE NOT NULL, " +
                "password_hash TEXT NOT NULL, " +
                "salt TEXT NOT NULL);";

        String createSpace = "CREATE TABLE IF NOT EXISTS space_marines (" +
                "id SERIAL PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "X INTEGER NOT NULL, " +
                "Y BIGINT NOT NULL, " +
                "datetime TIMESTAMP NOT NULL, " +
                "health DOUBLE PRECISION NOT NULL, " +
                "category astartes_category NOT NULL DEFAULT 'ASSAULT', " +
                "weapontype weapon_type, " +
                "meleeweapon melee_weapon_type NOT NULL, " +
                "chapter_name TEXT NOT NULL, " +
                "chapter_parent_legion TEXT NOT NULL, " +
                "chapter_marines_count INTEGER NOT NULL CHECK (chapter_marines_count > 0 AND chapter_marines_count <= 1000), " +
                "chapter_world TEXT NOT NULL, " +
                "owner_id INTEGER REFERENCES users(id) ON DELETE CASCADE);";

        Statement statement = dbManager.getConnection().createStatement();

        statement.executeUpdate(category);
        statement.executeUpdate(weapon);
        statement.executeUpdate(melee);

        statement.executeUpdate(createUsers);
        statement.executeUpdate(createSpace);
    }




    ArrayList<String> scriptPul = new ArrayList<String>();

    /**
     * Метод добавления скрипта
     */
    public synchronized void scriptInsert(String a) {
        scriptPul.add(a);
    }
    /**
     * Метод удаления элемента
     */
    public synchronized void scriptRemove(String a) {
        scriptPul.remove(a);
    }
    /**
     * Метод проверяющий содержится ли скрипт в текущем скриптпуле
     */
    public synchronized Boolean scriptIf(String a) {
        if (scriptPul.contains(a)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
