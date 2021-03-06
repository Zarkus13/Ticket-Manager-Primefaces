package com.supinfo.ticketmanager.web.controller;

import com.supinfo.ticketmanager.entity.Comment;
import com.supinfo.ticketmanager.entity.User;
import com.supinfo.ticketmanager.service.CommentService;
import com.supinfo.ticketmanager.service.UserService;
import fr.bargenson.util.faces.ControllerHelper;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class CommentController implements Serializable {

//	protected static final String ADD_COMMENT_OUTCOME = "showTicket?faces-redirect=true&includeViewParams=true";
	protected static final String ADD_COMMENT_OUTCOME = null;

	@Inject
	private TicketController ticketController;
	
	@Inject
	private ControllerHelper controllerHelper;
	
	@Inject
	private CommentService commentService;
	
	@Inject
	private UserService userService;

	private Comment comment;
    private boolean dialogAddCommentOpen;


    @PostConstruct
    public void init() {
        comment = new Comment();
    }

    public void openAddCommentDialog() {
        dialogAddCommentOpen = true;
    }
	
	public String addComment() {
		String username = controllerHelper.getUserPrincipal().getName();
		User author = userService.findUserByUsername(username);
		comment.setAuthor(author);
        comment.setTicket(ticketController.getTicketToShow());
		commentService.addComment(comment);

        dialogAddCommentOpen = false;

        ConfigurableNavigationHandler cnh = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
        cnh.performNavigation("/showTicket.jsf?ticketId=" + comment.getTicket().getId() + "&faces-redirect=true");
		
		return ADD_COMMENT_OUTCOME;
	}
	
	public Comment getComment() {
		return comment;
	}
	
	public void setComment(Comment comment) {
		this.comment = comment;
	}

    public boolean isDialogAddCommentOpen() {
        return dialogAddCommentOpen;
    }

    public void setDialogAddCommentOpen(boolean dialogAddCommentOpen) {
        this.dialogAddCommentOpen = dialogAddCommentOpen;
    }
}
