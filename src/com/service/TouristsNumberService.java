package com.service;

import com.dao.Factory;
import com.dao.TouristsNumberDao;
import com.dao.impl.TouristsNumberDaoImpl;

public class TouristsNumberService {
	private TouristsNumberDao dao = (TouristsNumberDao) Factory.dao(TouristsNumberDaoImpl.class.getName());

	public void insert(int num, int in, int out, String time) {
		dao.insert(num, in, out, time);
	}

}
