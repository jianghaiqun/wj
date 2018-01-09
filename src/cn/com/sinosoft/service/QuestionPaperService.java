package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.QuestionPaper;

public interface QuestionPaperService extends BaseService<QuestionPaper, String> {

	public void updateQuestion(QuestionPaper q, QuestionPaper newQP);     

	
}
