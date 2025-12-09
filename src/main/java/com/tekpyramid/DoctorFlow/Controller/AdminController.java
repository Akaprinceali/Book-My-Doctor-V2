package com.tekpyramid.DoctorFlow.Controller;

import com.tekpyramid.DoctorFlow.Constants.Status;
import com.tekpyramid.DoctorFlow.Entity.LeaveRequest;
import com.tekpyramid.DoctorFlow.Response.Success;
import com.tekpyramid.DoctorFlow.Service.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/admin")
@Tag(name = "Admin Leave Management", description = "APIs for managing employee leave requests")
public class AdminController {

    @Autowired
    private LeaveService leaveService;

    // 🧾 1️⃣ View all pending leave requests
    @Operation(
            summary = "Get all pending leave requests",
            description = "Fetches every leave request that is currently marked as PENDING.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pending leave requests fetched successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Success.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @GetMapping("/pending-leaves")
    public ResponseEntity<Success> getAllPendingLeaves() {
        List<LeaveRequest> pendingLeaves = leaveService.getPendingLeaves();

        Success response = new Success();
        response.setMessage("Pending leave requests fetched successfully");
        response.setError(false);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(pendingLeaves);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ✅ 2️⃣ Accept a leave request
    @Operation(
            summary = "Accept a leave request",
            description = "Updates a leave request's status to ACCEPTED using the provided leaveId.",
            parameters = {
                    @Parameter(name = "leaveId", description = "ID of the leave request", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Leave request accepted successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Success.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Leave request not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping("/accept-leave/{leaveId}")
    public ResponseEntity<Success> acceptLeave(@PathVariable int leaveId) {
        LeaveRequest updatedLeave = leaveService.updateLeaveStatus(leaveId, Status.ACCEPTED);

        Success response = new Success();
        response.setMessage("Leave request accepted successfully");
        response.setError(false);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(updatedLeave);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ❌ 3️⃣ Reject a leave request
    @Operation(
            summary = "Reject a leave request",
            description = "Updates a leave request's status to REJECTED using the provided leaveId.",
            parameters = {
                    @Parameter(name = "leaveId", description = "ID of the leave request", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Leave request rejected successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Success.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Leave request not found"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PutMapping("/reject-leave/{leaveId}")
    public ResponseEntity<Success> rejectLeave(@PathVariable int leaveId) {
        LeaveRequest updatedLeave = leaveService.updateLeaveStatus(leaveId, Status.REJECTED);

        Success response = new Success();
        response.setMessage("Leave request rejected successfully");
        response.setError(false);
        response.setHttpStatus(HttpStatus.OK);
        response.setData(updatedLeave);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
