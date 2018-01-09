package cn.com.sinosoft.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.SCAnswerDao;
import cn.com.sinosoft.dao.SCQuestionDao;
import cn.com.sinosoft.entity.QueryWGQ;
import cn.com.sinosoft.entity.SCAnswer;
import cn.com.sinosoft.service.SCAnswerService;



@Service
public class SCAnswerServiceImpl extends BaseServiceImpl<SCAnswer,String> implements SCAnswerService {}
