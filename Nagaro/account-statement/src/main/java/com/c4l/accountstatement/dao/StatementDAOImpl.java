package com.c4l.accountstatement.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.c4l.accountstatement.model.Statement;
import com.c4l.accountstatement.model.StatementRowMapper;






@Transactional
@Repository
public class StatementDAOImpl implements StatementDAO {

    private static final String SELECT_BY_ACCOUNT_NO = "select * from statement where account_id=? ";
    private final JdbcTemplate template;

    @Autowired
    public StatementDAOImpl(JdbcTemplate template) {
        this.template = template;
    }
    
    @Override
    public List<Statement> findByAccountNo(long accountId) {
        return template.query(SELECT_BY_ACCOUNT_NO, new StatementRowMapper(), accountId);
    }
    
    
	
}
