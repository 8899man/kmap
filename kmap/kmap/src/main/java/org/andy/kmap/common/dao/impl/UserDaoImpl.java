package org.andy.kmap.common.dao.impl;

import java.sql.*;

import org.andy.kmap.common.dao.ConnectionCloser;
import org.andy.kmap.common.dao.UserDao;
import org.andy.kmap.enums.RolesEnum;
import org.andy.kmap.common.model.entity.CommonResult;
import org.andy.kmap.common.model.entity.User;
import org.andy.kmap.tools.SendEmailTools;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("UserDaoImpl")
public class UserDaoImpl implements UserDao {

    private static Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Autowired
    @Qualifier("dataSource")
    private BasicDataSource dataSource;

    /**
     * This method adds a user.
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public CommonResult addUser(User user) {

        CommonResult commonResult=new CommonResult();

        Connection connection = null;
        PreparedStatement statement = null;
        SQLException exception = null;

        try {

            connection = this.dataSource.getConnection();
            //插入之前先查查邮箱是否唯一
            statement = connection.prepareStatement("SELECT id, name, email, passwd FROM user WHERE user.email = ?");

            statement.setString(1, user.getEmail());

            ResultSet result = statement.executeQuery();
            if(!result.first()){
                //插入语句
                statement = connection.prepareStatement("INSERT INTO user(name, email, passwd) VALUES(?, ?, ?)");
                statement.setString(1, user.getName());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getPassword());
                statement.executeUpdate();
                //角色表
                statement = connection.prepareStatement("INSERT INTO roles(userid, role) VALUES(?, ?)");
                statement.setString(1,user.getEmail());
                statement.setString(2, RolesEnum.NORMAL_USER.getRoleName());
                statement.executeUpdate();
                try {

                    SendEmailTools.sendMail(user.getEmail(),"邮箱激活","code验证码","html");

                }catch (Exception ex){

                    ex.printStackTrace();
                    commonResult.setStatus(-1);
                    commonResult.setDetail("发送邮箱失败，请确保邮箱有效！");
                    return commonResult;
                }
                //注册成功
                commonResult.setDetail("注册成功，请前往邮箱激活！");
                commonResult.setStatus(1);
                return commonResult;
            }
            else
            {
                commonResult.setDetail("该邮箱已经注册过了！");
                commonResult.setStatus(-1);
                return commonResult;

            }

        }catch (SQLException ex){
            exception = ex;
            commonResult.setStatus(-1);
            commonResult.setDetail("数据库服务器异常！");
            return commonResult;

        }
        catch (Exception ex) {

            commonResult.setStatus(-1);
            commonResult.setDetail("服务器内部错误！");
            return commonResult;

        } finally {

            ConnectionCloser.close(connection,statement,exception);

        }
    }


    /**
     * This method gets a user by email.
     * @param email
     * @return
     */
    public User getUser(String email) {

        Connection connection = null;
        PreparedStatement statement = null;
        SQLException exception = null;
        User user = null;
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement("SELECT id,name, email, passwd,eid,epasswd,major FROM user WHERE user.email = ?");

            statement.setString(1, email);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                user = new User(result.getString(2), result.getString(3), result.getString(4));
                user.setId(result.getInt(1));
                if(result.getObject(5)!=null&&result.getObject(6)!=null)
                {
                    user.setEid(result.getString("eid"));
                    user.setEpassword(result.getString("epasswd"));
                }
                if(result.getObject(7)!=null)
                {
                    user.setMajorId(result.getInt(7));
                }
                else
                {
                    user.setMajorId(0);
                }
            }
        } catch (SQLException ex) {
            exception = ex;
            logger.info("UserDaoImpl getUser error:",ex);
        } finally {
            ConnectionCloser.close(connection, statement, exception);
        }

        return user;
    }

    /**
     * 获取用户角色
     * @param email
     * @return
     */
    public String getUserRole(String email){

        Connection connection = null;
        PreparedStatement statement = null;
        SQLException exception = null;

        String role=null;
        try {
            connection = this.dataSource.getConnection();
            statement = connection.prepareStatement("SELECT role FROM roles WHERE userid = ?");

            statement.setString(1, email);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                role=result.getString(1);
            }
        } catch (SQLException ex) {
            exception = ex;
        } finally {
            ConnectionCloser.close(connection, statement, exception);
        }

        return role;

    }

    /**
     * 完善用户信息
     * @param user
     * @return
     */
    public CommonResult addUserInfo(User user){
        CommonResult commonResult=new CommonResult();

        Connection connection = null;
        PreparedStatement statement = null;
        SQLException exception = null;

        try {


            connection = this.dataSource.getConnection();
            //插入之前先查查邮箱是否唯一
            statement = connection.prepareStatement("UPDATE user SET eid=?, epasswd=?, major=?,name=?  WHERE email = ?");

            statement.setString(1, user.getEid());
            statement.setString(2,user.getEpassword());
            statement.setInt(3,user.getMajorId());
            statement.setString(4,user.getName());
            statement.setString(5,user.getEmail());


            statement.executeUpdate();
            commonResult.setDetail("信息更新成功");
            commonResult.setStatus(1);
            return commonResult;


        }catch (SQLException ex){
            exception = ex;
            commonResult.setStatus(-1);
            commonResult.setDetail("数据库服务器异常！");
            return commonResult;

        }
        catch (Exception ex) {

            commonResult.setStatus(-1);
            commonResult.setDetail("服务器内部错误！");
            return commonResult;

        } finally {

            ConnectionCloser.close(connection,statement,exception);

        }
    }

}
