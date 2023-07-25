package org.example.repository.impl;

import org.example.model.Position;
import org.example.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PositionRepositoryImpl implements PositionRepository {

    private final String GET_POSITION_BY_ID = "select * from positions where id = ?;";
    private final String GET_ALL_POSITIONS = "select * from positions;";
    private final String SAVE_POSITION = "insert into positions (name) values (?);";
    private final String UPDATE_POSITION = "update positions set name = ? where id = ?;";
    private final String DELETE_POSITION = "delete from positions where id = ?;";


    private final Connection connection;

    @Autowired
    public PositionRepositoryImpl(Connection connection) {this.connection = connection;}

    @Override
    public Optional<Position> get(int id) {
        try (PreparedStatement prst =  connection.prepareStatement(GET_POSITION_BY_ID)){
            prst.setInt(1, id);

            ResultSet resultSet = prst.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            Position position = new Position();
            position.setId(resultSet.getInt("id"));
            position.setName(resultSet.getString("name"));
            return Optional.of(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Position> getAll() {
        List<Position> positions = new ArrayList<>();
        try (PreparedStatement prst =  connection.prepareStatement(GET_ALL_POSITIONS)){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                Position position = new Position();
                position.setId(resultSet.getInt("id"));
                position.setName(resultSet.getString("name"));

                positions.add(position);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    @Override
    public boolean save(Position position) {
        try (PreparedStatement prst =  connection.prepareStatement(SAVE_POSITION)){
            prst.setString(1, position.getName());

            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void update(Position position) {
        try (PreparedStatement prst =  connection.prepareStatement(UPDATE_POSITION)){
            prst.setString(1, position.getName());

            prst.setInt(2, position.getId());
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Position position) {
        try (PreparedStatement prst =  connection.prepareStatement(DELETE_POSITION)){
            prst.setInt(1, position.getId());
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
