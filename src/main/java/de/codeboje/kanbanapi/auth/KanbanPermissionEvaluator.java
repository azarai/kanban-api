package de.codeboje.kanbanapi.auth;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import de.codeboje.kanbanapi.model.Board;
import de.codeboje.kanbanapi.model.Task;


@Component
public class KanbanPermissionEvaluator implements PermissionEvaluator{

	@Override
	public boolean hasPermission(Authentication authentication, Object target, Object permission) {
		
		final Long loggedInUserId = ((UserPrincipal) authentication.getPrincipal()).getId();
		
		if (target == null) {
			return true;
		}
		else if(target instanceof Board) {
			if( loggedInUserId.equals(((Board) target).getUser())) {
				return true;
			}
		} else if(target instanceof Task) {
			if( loggedInUserId.equals( ((Task) target).getBoard().getUser())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId,
			String targetType, Object permission) {
		return false;
	}

}
