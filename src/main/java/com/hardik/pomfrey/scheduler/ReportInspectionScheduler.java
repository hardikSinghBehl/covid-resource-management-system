package com.hardik.pomfrey.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hardik.pomfrey.service.CommentService;
import com.hardik.pomfrey.service.RequestService;
import com.hardik.pomfrey.service.ResourceService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReportInspectionScheduler {

	private final RequestService requestService;

	private final ResourceService resourceService;

	private final CommentService commentService;

	@Scheduled(cron = "0 0 * ? * *")
	public void reportInspectionJob() {
		requestService.handleReports();
		resourceService.handleReports();
		commentService.handleReports();
	}

}
