package com.mfangsoft.zhuangjialong.integration.introduce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mfangsoft.zhuangjialong.common.context.UserContext;
import com.mfangsoft.zhuangjialong.common.model.ResponseMessage;
import com.mfangsoft.zhuangjialong.common.utils.SysConstant;
import com.mfangsoft.zhuangjialong.integration.enterprise.mapper.BuildClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.enterprise.model.BuildClassEntity;
import com.mfangsoft.zhuangjialong.integration.enums.UserType;
import com.mfangsoft.zhuangjialong.integration.introduce.mapper.IntroduceClassEntityMapper;
import com.mfangsoft.zhuangjialong.integration.introduce.mapper.IntroduceProductEntityMapper;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceClass;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceClassEntity;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceProductEntity;
import com.mfangsoft.zhuangjialong.integration.introduce.model.IntroduceProductParam;
import com.mfangsoft.zhuangjialong.integration.introduce.model.PartnerClass;
import com.mfangsoft.zhuangjialong.integration.introduce.service.IntroduceClassService;
import com.mfangsoft.zhuangjialong.integration.partner.model.PartnerEntity;
import com.mfangsoft.zhuangjialong.integration.user.model.UserEntity;

@Service
public class IntroduceClassServiceImpl implements IntroduceClassService {

	@Autowired
	IntroduceClassEntityMapper introduceClassEntityMapper;
	@Autowired
	IntroduceProductEntityMapper introduceProductEntityMapper;
	@Autowired
	BuildClassEntityMapper buildClassEntityMapper;
	
	@Override
	public List<PartnerClass> getAllPartnerClassWithIntroState() {

		Long partner_id = null;
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.PARTNER.getIndex()) {
			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			partner_id = p.getId();
			if (partner_id == null)
				return null;
		} else {
			return null;
		}
		return introduceClassEntityMapper.selectPartnerClass(partner_id);
	}

	@Override
	public boolean addIntroduceClass(IntroduceClassEntity record) {

		Long partner_id = null;
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.PARTNER.getIndex()) {
			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			partner_id = p.getId();
			if (partner_id == null)
				return false;
		} else {
			return false;
		}

		List<IntroduceClassEntity> list = introduceClassEntityMapper.selectByPartner(partner_id);
		if (list == null) {
			record.setPartner_id(partner_id);
			record.setProduct_num(0);
			record.setSort(0);
			introduceClassEntityMapper.insertSelective(record);
		} else {
			// 判断已添加品类
			for (IntroduceClassEntity entity : list) {
				if (entity.getClass_id().equals(record.getClass_id())) {
					return false;
				}
			}
			record.setPartner_id(partner_id);
			record.setProduct_num(0);
			record.setSort(list.get(list.size() - 1).getSort() + 1);
			introduceClassEntityMapper.insertSelective(record);
		}
		return true;
	}

	/**
	 * 
	 * @param partner_id
	 * @param record
	 *            需要包含 class_id class_name
	 * @return
	 */
	private Long addIntroduceClass(Long partner_id, IntroduceClassEntity record) {

		if (partner_id == null) {
			return null;
		}

		List<IntroduceClassEntity> list = introduceClassEntityMapper.selectByPartner(partner_id);
		if (list == null || list.size() == 0) {
			record.setPartner_id(partner_id);
			record.setProduct_num(0);
			record.setSort(0);
			introduceClassEntityMapper.insertSelective(record);
			return record.getId();
		} else {
			// 判断已添加品类
			for (IntroduceClassEntity entity : list) {
				if (entity.getClass_id().equals(record.getClass_id())) {
					return entity.getId();
				}
			}
			int sort = list.get(0).getSort();
			record.setPartner_id(partner_id);
			record.setProduct_num(0);
			record.setSort(++sort);
			introduceClassEntityMapper.insertSelective(record);
			return record.getId();
		}

	}

	@Override
	public List<IntroduceClassEntity> queryIntroduceClass() {

		Long partner_id = null;
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.PARTNER.getIndex()) {
			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			partner_id = p.getId();
			if (partner_id == null)
				return null;
		} else {
			return null;
		}
		
		return introduceClassEntityMapper.selectByPartner(partner_id);
	}

	@Override
	public void deleteIntroduceClass(Long id) {

		introduceProductEntityMapper.deleteByIntroduceId(id);

		introduceClassEntityMapper.deleteByPrimaryKey(id);

	}

	@Override
	public boolean modifyIntroduceClass(Long id1, Long id2) {

		IntroduceClassEntity entity1 = introduceClassEntityMapper.selectByPrimaryKey(id1);

		IntroduceClassEntity entity2 = introduceClassEntityMapper.selectByPrimaryKey(id2);

		int sort = entity1.getSort();
		entity1.setSort(entity2.getSort());
		entity2.setSort(sort);

		introduceClassEntityMapper.updateByPrimaryKeySelective(entity1);
		introduceClassEntityMapper.updateByPrimaryKeySelective(entity2);

		return true;
	}

	@Override
	public ResponseMessage addIntroduceProduct(IntroduceProductParam param, ResponseMessage responseMessage) {

		Long partner_id = null;
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.PARTNER.getIndex()) {
			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			partner_id = p.getId();
			if (partner_id == null){
				responseMessage.setCode(SysConstant.FAILURE_CODE);
				responseMessage.setMessage("未找到合伙人");
				return responseMessage;
			}
		} else {
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage("权限不够");
			return responseMessage;
		}
		// 增加品类
		IntroduceClassEntity entity = new IntroduceClassEntity();
		entity.setClass_id(param.getClass_id());

		BuildClassEntity buildClassEntity = buildClassEntityMapper.selectByPrimaryKey(param.getClass_id());
		if (buildClassEntity == null){
			responseMessage.setCode(SysConstant.FAILURE_CODE);
			responseMessage.setMessage("未找到品类");
			return responseMessage;
		}
		entity.setClass_name(buildClassEntity.getName());

		Long introduce_id = addIntroduceClass(partner_id, entity);
		//

		List<IntroduceProductEntity> list = introduceProductEntityMapper.selectAllByIntroduceId(introduce_id);

		if (list == null || list.size() <= 0) {
			int flag = 0 ;
			for(int i=0; i < param.getProduct_id_array().length && i < 5; i++){
			
				IntroduceProductEntity record = new IntroduceProductEntity();
				record.setClass_id(param.getClass_id());
				record.setProduct_id(param.getProduct_id_array()[i]);
				record.setIntroduce_id(introduce_id);
				record.setSort(i);
				introduceProductEntityMapper.insertSelective(record);
				
				flag++;
			//成功
			}
			
			IntroduceClassEntity en = introduceClassEntityMapper.selectByPrimaryKey(introduce_id);
			en.setProduct_num(en.getProduct_num() + flag);
			introduceClassEntityMapper.updateByPrimaryKeySelective(en);
			
		} else {
			if (list.size() >= 5){
				responseMessage.setCode(SysConstant.FAILURE_CODE);
				responseMessage.setMessage("超过限制产品个数");
				return responseMessage;// 大于5款
			}else{
				for(int i=0; i < param.getProduct_id_array().length; i++){
					for(IntroduceProductEntity e: list){
						if(e.getProduct_id().equals(param.getProduct_id_array()[i])){
							responseMessage.setCode(SysConstant.FAILURE_CODE);
							responseMessage.setMessage("产品重复");
							return responseMessage;
						}
					}
				}
				int size = list.size();
				int flag = 0 ;
				
				int old_sort = list.get(0).getSort();
				for(int i=0; i < param.getProduct_id_array().length && i < (5 - size); i++){
					
					IntroduceProductEntity record = new IntroduceProductEntity();
					record.setClass_id(param.getClass_id());
					record.setProduct_id(param.getProduct_id_array()[i]);
					record.setIntroduce_id(introduce_id);
					record.setSort(++old_sort);
					introduceProductEntityMapper.insertSelective(record);
					//成功
					flag++;
				}
				
				IntroduceClassEntity en = introduceClassEntityMapper.selectByPrimaryKey(introduce_id);
				en.setProduct_num(en.getProduct_num() + flag);
				introduceClassEntityMapper.updateByPrimaryKeySelective(en);
				
			}
		}

		responseMessage.setCode(SysConstant.SUCCESS_CODE);
		responseMessage.setMessage(SysConstant.SUCCESS_MSG);
		return responseMessage;
	}

	@Override
	public List<IntroduceClass> queryIntroduceProduct() {

		Long partner_id = null;
		UserEntity u = UserContext.getCurrentUser();
		if (u.getUser_type() == UserType.PARTNER.getIndex()) {
			PartnerEntity p = (PartnerEntity) UserContext.getCurrentUserInfo();
			partner_id = p.getId();
			if (partner_id == null)
				return null;
		} else {
			return null;
		}
		
		return introduceProductEntityMapper.selectClassAndProduct(partner_id);
	}

	@Override
	public boolean addClassShowImg(BuildClassEntity record) {
		
		if(record.getId() != null && (record.getAppshow_imgurl() != null || record.getRgb() != null)){
			return buildClassEntityMapper.updateByPrimaryKeySelective(record) > 0;
		}
		return false;
	}

	@Override
	public boolean deleteIntroduceProduct(Long introduce_id, Long introduce_product_id) {

		int flag1 = introduceProductEntityMapper.deleteByPrimaryKey(introduce_product_id);
		
		IntroduceClassEntity entity = introduceClassEntityMapper.selectByPrimaryKey(introduce_id);
		entity.setProduct_num(entity.getProduct_num() - 1);
		
		int flag2 = introduceClassEntityMapper.updateByPrimaryKeySelective(entity);
		
		return flag1 >0 && flag2 > 0;
		
	}

}
