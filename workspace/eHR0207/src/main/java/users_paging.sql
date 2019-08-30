SELECT T1.*,T2.*
FROM(
    SELECT  B.u_id,
            B.name,
            B.passwd,
            B.h_level,
            B.login,
            B.recommend,
            B.email,
            TO_CHAR(B.reg_dt,'YYYY/MM/DD') reg_dt
    FROM(
            SELECT ROWNUM AS rnum
                  ,A.*
            FROM(
                SELECT *
                FROM users
                --SEARCH CONDITION
                WHERE u_id LIKE '%125%'
                ORDER BY reg_dt
            )A
            WHERE ROWNUM <=(10*(1-1)+10))B
            WHERE B.rnum>= (10*(1-1)+1)
		)T1
CROSS JOIN
(
	SELECT COUNT(*) total_cnt
	FROM users
    --SEARCH CONDITION
    WHERE u_id LIKE '%125%'
)T2
