package com.andreidadushko.tomography2017.dao.xml.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.andreidadushko.tomography2017.dao.db.ICategoryDao;
import com.andreidadushko.tomography2017.dao.db.IOfferDao;
import com.andreidadushko.tomography2017.dao.db.IStudyOfferCartDao;
import com.andreidadushko.tomography2017.dao.db.custom.models.StudyOfferCartForList;
import com.andreidadushko.tomography2017.dao.xml.impl.wrapper.XmlModelWrapper;
import com.andreidadushko.tomography2017.datamodel.Category;
import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;

@Repository
public class StudyOfferCartDaoXmlImpl extends AbstractDaoXmlImpl<StudyOfferCart> implements IStudyOfferCartDao {

	@Inject
	private IOfferDao offerDao;

	@Inject
	private ICategoryDao categoryDao;

	@Override
	public File getFile() {
		File file = new File(rootFolder + "study_offer_cart.xml");
		return file;
	}

	@Override
	public boolean idEquals(StudyOfferCart object, Integer id) {
		return object.getId().equals(id);
	}

	@Override
	public void setId(StudyOfferCart object, int id) {
		object.setId(id);
	}

	@Override
	public void updateObject(List<StudyOfferCart> list, StudyOfferCart object) {
		for (StudyOfferCart studyOfferCart : list) {
			if (studyOfferCart.getId().equals(object.getId())) {
				studyOfferCart.setPaid(object.getPaid());
				studyOfferCart.setPayDate(object.getPayDate());
				studyOfferCart.setStudyId(object.getStudyId());
				studyOfferCart.setOfferId(object.getOfferId());
				break;
			}
		}
	}

	@Override
	public void massDelete(Integer[] studyIdArray) {
		if (studyIdArray != null && studyIdArray.length != 0) {
			File file = getFile();
			XmlModelWrapper<StudyOfferCart> wrapper = (XmlModelWrapper<StudyOfferCart>) xstream.fromXML(file);
			List<StudyOfferCart> list = wrapper.getRows();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				StudyOfferCart studyOfferCart = (StudyOfferCart) iterator.next();
				for (int i = 0; i < studyIdArray.length; i++) {
					if (studyOfferCart.getStudyId().equals(studyIdArray[i])) {
						iterator.remove();
						break;
					}
				}
			}
			writeNewData(file, wrapper);
		}
	}

	@Override
	public List<StudyOfferCartForList> getStudyOfferCartByStudyId(Integer studyId) {
		List<StudyOfferCartForList> result = new ArrayList<>();
		if (studyId != null) {
			File file = getFile();
			XmlModelWrapper<StudyOfferCart> wrapper = (XmlModelWrapper<StudyOfferCart>) xstream.fromXML(file);
			List<StudyOfferCart> list = wrapper.getRows();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				StudyOfferCart studyOfferCart = (StudyOfferCart) iterator.next();
				if (studyOfferCart.getStudyId().equals(studyId)) {
					StudyOfferCartForList tempCart = new StudyOfferCartForList();
					tempCart.setId(studyOfferCart.getId());
					tempCart.setPaid(studyOfferCart.getPaid());
					tempCart.setPayDate(studyOfferCart.getPayDate());
					Offer offer = offerDao.get(studyOfferCart.getOfferId());
					Category category = categoryDao.get(offer.getCategorId());
					tempCart.setCategory(category.getName());
					tempCart.setOffer(offer.getName());
					tempCart.setPrice(offer.getPrice());
					result.add(tempCart);
				}
			}
		}
		return result;
	}

}
