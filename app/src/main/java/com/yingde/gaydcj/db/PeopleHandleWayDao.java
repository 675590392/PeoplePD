package com.yingde.gaydcj.db;

import android.text.TextUtils;

import com.yingde.gaydcj.application.PeopleApplication;
import com.yingde.gaydcj.entity.db.D_BYZK;
import com.yingde.gaydcj.entity.db.D_BYZKDao;
import com.yingde.gaydcj.entity.db.D_FWLX;
import com.yingde.gaydcj.entity.db.D_FWLXDao;
import com.yingde.gaydcj.entity.db.D_GJ;
import com.yingde.gaydcj.entity.db.D_GJDao;
import com.yingde.gaydcj.entity.db.D_HYZK;
import com.yingde.gaydcj.entity.db.D_HYZKDao;
import com.yingde.gaydcj.entity.db.D_JCW;
import com.yingde.gaydcj.entity.db.D_JCWDao;
import com.yingde.gaydcj.entity.db.D_JZ;
import com.yingde.gaydcj.entity.db.D_JZDao;
import com.yingde.gaydcj.entity.db.D_JZFWLX;
import com.yingde.gaydcj.entity.db.D_JZFWLXDao;
import com.yingde.gaydcj.entity.db.D_LHSY;
import com.yingde.gaydcj.entity.db.D_LHSYDao;
import com.yingde.gaydcj.entity.db.D_MLPH;
import com.yingde.gaydcj.entity.db.D_MLPHDao;
import com.yingde.gaydcj.entity.db.D_MZ;
import com.yingde.gaydcj.entity.db.D_MZDao;
import com.yingde.gaydcj.entity.db.D_PCS;
import com.yingde.gaydcj.entity.db.D_PCSDao;
import com.yingde.gaydcj.entity.db.D_PROVINCE;
import com.yingde.gaydcj.entity.db.D_PROVINCEDao;
import com.yingde.gaydcj.entity.db.D_RKLB;
import com.yingde.gaydcj.entity.db.D_RKLBDao;
import com.yingde.gaydcj.entity.db.D_ROAD;
import com.yingde.gaydcj.entity.db.D_ROADDao;
import com.yingde.gaydcj.entity.db.D_WDDM;
import com.yingde.gaydcj.entity.db.D_WDDMDao;
import com.yingde.gaydcj.entity.db.D_WHCD;
import com.yingde.gaydcj.entity.db.D_WHCDDao;
import com.yingde.gaydcj.entity.db.D_XB;
import com.yingde.gaydcj.entity.db.D_XBDao;
import com.yingde.gaydcj.entity.db.D_XGY;
import com.yingde.gaydcj.entity.db.D_XGYDao;
import com.yingde.gaydcj.entity.db.D_XX;
import com.yingde.gaydcj.entity.db.D_XXDao;
import com.yingde.gaydcj.entity.db.D_XZQH;
import com.yingde.gaydcj.entity.db.D_XZQHDao;
import com.yingde.gaydcj.entity.db.D_YFZGX;
import com.yingde.gaydcj.entity.db.D_YFZGXDao;
import com.yingde.gaydcj.entity.db.D_YHZGX;
import com.yingde.gaydcj.entity.db.D_YHZGXDao;
import com.yingde.gaydcj.entity.db.D_ZD;
import com.yingde.gaydcj.entity.db.D_ZDDao;
import com.yingde.gaydcj.entity.db.D_ZJLX;
import com.yingde.gaydcj.entity.db.D_ZJLXDao;
import com.yingde.gaydcj.entity.db.D_ZJXY;
import com.yingde.gaydcj.entity.db.D_ZJXYDao;
import com.yingde.gaydcj.entity.db.D_ZYLB;
import com.yingde.gaydcj.entity.db.D_ZYLBDao;
import com.yingde.gaydcj.entity.db.D_ZZMM;
import com.yingde.gaydcj.entity.db.D_ZZMMDao;
import com.yingde.gaydcj.entity.db.LABELS;
import com.yingde.gaydcj.entity.db.LABELSDao;
import com.yingde.gaydcj.util.ChinaInitialUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class PeopleHandleWayDao {
    /**
     * 模糊查找数据 街路巷（道路）
     */
    public List<D_ROAD> queryRoadDictionary(String edJzlName) {
        // 中文或者英文都转换成小写字母首拼
        String firstName = ChinaInitialUtil.getPinYinHeadChar(edJzlName);
//        QueryBuilder<words> qb = words_Dao.queryBuilder();
//        qb.where(Properties.Lesson_id.like("%"+"你需要搜的"+"%"));
        List joes = PeopleApplication.getDaoInstant().getD_ROADDao().queryBuilder()  // 查询 User
                .where(D_ROADDao.Properties.PY.like("%" + firstName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }

    /**
     * 模糊查找数据  门弄牌号
     */
    public List<D_MLPH> queryMLPHDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_MLPHDao().queryBuilder()  // 查询 User
                .where(D_MLPHDao.Properties.MLPH.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }

    /**
     * 模糊查找数据  居村委
     */
    public List<D_JCW> queryJCWDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_JCWDao().queryBuilder()  // 查询 User
                .where(D_JCWDao.Properties.JCWMC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }

    /**
     * 模糊查找数据  派出所
     */
    public List<D_PCS> queryPCSDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_PCSDao().queryBuilder()  // 查询 User
                .where(D_PCSDao.Properties.PCSMC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  街镇
     */
    public List<D_JZ> queryJZDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_JZDao().queryBuilder()  // 查询 User
                .where(D_JZDao.Properties.JZMC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }

    /**
     * 模糊查找数据  道路
     */
    public List<D_ROAD> queryROADDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_ROADDao().queryBuilder()  // 查询 User
                .where(D_ROADDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }

    /**
     * 模糊查找数据  D_BYZK
     */
    public List<D_BYZK> queryBYZKDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_BYZKDao().queryBuilder()  // 查询 User
                .where(D_BYZKDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }

    /**
     * 模糊查找数据  房屋类型
     */
    public List<D_FWLX> queryFWLXDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_FWLXDao().queryBuilder()  // 查询 User
                .where(D_FWLXDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  国家
     */
    public List<D_GJ> queryGJDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_GJDao().queryBuilder()  // 查询 User
                .where(D_GJDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }

    /**
     * 模糊查找数据  D_HYZK
     */
    public List<D_HYZK> queryHYZKDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_HYZKDao().queryBuilder()  // 查询 User
                .where(D_HYZKDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }

    /**
     * 模糊查找数据  D_JZFWLX
     */
    public List<D_JZFWLX> queryJZFWLXDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_JZFWLXDao().queryBuilder()  // 查询 User
                .where(D_JZFWLXDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }

    /**
     * 模糊查找数据  D_LHSY
     */
    public List<D_LHSY> queryLHSYDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_LHSYDao().queryBuilder()  // 查询 User
                .where(D_LHSYDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  民族
     */
    public List<D_MZ> queryMZDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_MZDao().queryBuilder()  // 查询 User
                .where(D_MZDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  省
     */
    public List<D_PROVINCE> queryPROVINCEDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_PROVINCEDao().queryBuilder()  // 查询 User
                .where(D_PROVINCEDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  D_RKLB
     */
    public List<D_RKLB> queryRKLBDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_RKLBDao().queryBuilder()  // 查询 User
                .where(D_RKLBDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  D_WDDM
     */
    public List<D_WDDM> queryWDDMDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_WDDMDao().queryBuilder()  // 查询 User
                .where(D_WDDMDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  D_WHCD
     */
    public List<D_WHCD> queryWHCDDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_WHCDDao().queryBuilder()  // 查询 User
                .where(D_WHCDDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  性别
     */
    public List<D_XB> queryXBDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_XBDao().queryBuilder()  // 查询 User
                .where(D_XBDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  协管员
     */
    public List<D_XGY> queryXGYDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_XGYDao().queryBuilder()  // 查询 User
                .where(D_XGYDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  D_XX
     */
    public List<D_XX> queryXXDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_XXDao().queryBuilder()  // 查询 User
                .where(D_XXDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  D_YFZGX
     */
    public List<D_YFZGX> queryYFZGXDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_YFZGXDao().queryBuilder()  // 查询 User
                .where(D_YFZGXDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  行政区划
     */
    public List<D_XZQH> queryXZQHDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_XZQHDao().queryBuilder()  // 查询 User
                .where(D_XZQHDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  D_YHZGX
     */
    public List<D_YHZGX> queryYHZGXDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_YHZGXDao().queryBuilder()  // 查询 User
                .where(D_YHZGXDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  D_ZD
     */
    public List<D_ZD> queryZDDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_ZDDao().queryBuilder()  // 查询 User
                .where(D_ZDDao.Properties.TABLENAME.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  D_ZJLX
     */
    public List<D_ZJLX> queryZJLXDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_ZJLXDao().queryBuilder()  // 查询 User
                .where(D_ZJLXDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  D_ZJXY
     */
    public List<D_ZJXY> queryZJXYDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_ZJXYDao().queryBuilder()  // 查询 User
                .where(D_ZJXYDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  D_ZYLB
     */
    public List<D_ZYLB> queryZYLBDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_ZYLBDao().queryBuilder()  // 查询 User
                .where(D_ZYLBDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 模糊查找数据  政治面貌
     */
    public List<D_ZZMM> queryZZMMDictionary(String edJzlName) {
        List joes = PeopleApplication.getDaoInstant().getD_ZZMMDao().queryBuilder()  // 查询 User
                .where(D_ZZMMDao.Properties.MC.like("%" + edJzlName + "%"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 查找数据  查询展示全部居村委
     */
    public List<D_JCW> queryJCWDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_JCWDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部派出所
     */
    public List<D_PCS> queryPCSDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_PCSDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部街镇
     */
    public List<D_JZ> queryJZDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_JZDao().loadAll();
        return joes;
    }

    /**
     * 查找数据  查询展示全部门弄牌号
     */
    public List<D_MLPH> queryMLPHDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_MLPHDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部道路
     */
    public List<D_ROAD> queryROADDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_ROADDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部D_BYZK
     */
    public List<D_BYZK> queryBYZKDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_BYZKDao().loadAll();
        return joes;
    }

    /**
     * 查找数据  查询展示全部房屋类型
     */
    public List<D_FWLX> queryFWLXDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_FWLXDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部国家
     */
    public List<D_GJ> queryGJDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_GJDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部D_HYZK
     */
    public List<D_HYZK> queryHYZKDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_HYZKDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部D_JZFWLX
     */
    public List<D_JZFWLX> queryJZFWLXDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_JZFWLXDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部D_LHSY
     */
    public List<D_LHSY> queryLHSYDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_LHSYDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部民族
     */
    public List<D_MZ> queryMZDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_MZDao().loadAll();
        return joes;
    }

    /**
     * 查找数据  查询展示全部省份
     */
    public List<D_PROVINCE> queryPROVINCEDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_PROVINCEDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部D_RKLB
     */
    public List<D_RKLB> queryRKLBDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_RKLBDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部D_WDDM
     */
    public List<D_WDDM> queryWDDMDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_WDDMDao().loadAll();
        return joes;
    }

    /**
     * 查找数据  查询展示全部D_WHCD
     */
    public List<D_WHCD> queryWHCDDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_WHCDDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部性别
     */
    public List<D_XB> queryXBDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_XBDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部协管员
     */
    public List<D_XGY> queryXGYDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_XGYDao().loadAll();
        return joes;
    }

    /**
     * 查找数据  查询展示全部D_XX
     */
    public List<D_XX> queryXXDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_XXDao().loadAll();
        return joes;
    }

    /**
     * 查找数据  查询展示全部D_YFZGX
     */
    public List<D_YFZGX> queryYFZGXDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_YFZGXDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部D_HYZK
     */
    public List<D_XZQH> queryXZQHDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_XZQHDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部D_YHZGX
     */
    public List<D_YHZGX> queryYHZGXDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_YHZGXDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部D_ZD
     */
    public List<D_ZD> queryZDDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_ZDDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部D_ZJLX
     */
    public List<D_ZJLX> queryZJLXDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_ZJLXDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部D_ZJXY
     */
    public List<D_ZJXY> queryZJXYDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_ZJXYDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部D_ZYLB
     */
    public List<D_ZYLB> queryZYLBDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_ZYLBDao().loadAll();
        return joes;
    }
    /**
     * 查找数据  查询展示全部政治面貌
     */
    public List<D_ZZMM> queryZZMMDictionaryAll() {
        List joes = PeopleApplication.getDaoInstant().getD_ZZMMDao().loadAll();
        return joes;
    }

    /**
     * 查找数据  查询展示全部人员标识
     */
    public List<LABELS> queryLABELSDictionaryPERSONLABLE() {
//        List joes = PeopleApplication.getDaoInstant().getLABELSDao().loadAll();
        List joes = PeopleApplication.getDaoInstant().getLABELSDao().queryBuilder()  // 查询 User
                .where(LABELSDao.Properties.TYPE.like("Persons"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }
    /**
     * 查找数据  查询展示全部房屋标识
     */
    public List<LABELS> queryLABELSDictionaryHOUSELABLE() {
        List joes = PeopleApplication.getDaoInstant().getLABELSDao().queryBuilder()  // 查询 User
                .where(LABELSDao.Properties.TYPE.eq("Houses"))  // 首名为 Joe
                .list();  // 返回集合
        return joes;
    }




    /**
     * 查找数据  通过编码查找名称  协管员
     */
    public List<D_XGY>  queryD_XGYDictionaryToDM(String dm) {
        if(TextUtils.isEmpty(dm)){
            return null;
        }
        List mc = PeopleApplication.getDaoInstant().getD_XGYDao().queryBuilder()  // 查询 User
                .where(D_XGYDao.Properties.DM.eq(dm))  // 首名为 Joe
                .list();  // 返回集合
        return mc;
    }
    /**
     * 查找数据  通过编码查找名称  派出所
     */
    public List<D_PCS>  queryD_PCSDictionaryToDM(String dm) {
        if(TextUtils.isEmpty(dm)){
            return null;
        }
        List mc = PeopleApplication.getDaoInstant().getD_PCSDao().queryBuilder()  // 查询 User
                .where(D_PCSDao.Properties.PCS6.eq(dm))  // 首名为 Joe
                .list();  // 返回集合
        return mc;
    }
    /**
     * 查找数据  通过编码查找名称  街镇
     */
    public List<D_JZ>  queryD_JZDictionaryToDM(String dm) {
        if(TextUtils.isEmpty(dm)){
            return null;
        }
        List mc = PeopleApplication.getDaoInstant().getD_JZDao().queryBuilder()  // 查询 User
                .where(D_JZDao.Properties.JZDM.eq(dm))  // 首名为 Joe
                .list();  // 返回集合
        return mc;
    }
    /**
     * 查找数据  通过编码查找名称  居村委
     */
    public List<D_JCW>  queryD_JCWDictionaryToDM(String dm) {
        if(TextUtils.isEmpty(dm)){
            return null;
        }
        List mc = PeopleApplication.getDaoInstant().getD_JCWDao().queryBuilder()  // 查询 User
                .where(D_JCWDao.Properties.JCWDM.eq(dm))  // 首名为 Joe
                .list();  // 返回集合
        return mc;
    }

    /**
     * 查找数据  通过编码查找名称  网点
     */
    public List<D_WDDM>  queryD_WDDMDictionaryToDM(String dm) {
        if(TextUtils.isEmpty(dm)){
            return null;
        }
        List mc = PeopleApplication.getDaoInstant().getD_WDDMDao().queryBuilder()  // 查询 User
                .where(D_WDDMDao.Properties.DM.eq(dm))  // 首名为 Joe
                .list();  // 返回集合
        return mc;
    }
    /**
     * 查找数据  通过名称查找编码  民族
     */
    public List<D_MZ>  queryD_MZDictionaryToDM(String mc) {
        if(TextUtils.isEmpty(mc)){
            return null;
        }
        List bm = PeopleApplication.getDaoInstant().getD_MZDao().queryBuilder()  // 查询 User
                .where(D_MZDao.Properties.MC.eq(mc))  // 首名为 Joe
                .list();  // 返回集合
        return bm;
    }
    /**
     * 查找数据  通过编码查找名称  性别
     */
    public List<D_MZ>  queryD_MZDictionaryToMC(String dm) {
        if(TextUtils.isEmpty(dm)){
            return null;
        }
        List mc = PeopleApplication.getDaoInstant().getD_MZDao().queryBuilder()  // 查询 User
                .where(D_MZDao.Properties.DM.eq(dm))  // 首名为 Joe
                .list();  // 返回集合
        return mc;
    }


}
