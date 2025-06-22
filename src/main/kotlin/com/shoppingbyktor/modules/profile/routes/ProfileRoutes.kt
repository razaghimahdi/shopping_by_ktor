package com.shoppingbyktor.modules.profile.routes

import com.shoppingbyktor.modules.profile.controller.ProfileController
import com.shoppingbyktor.database.models.user.body.UserProfileRequest

import com.shoppingbyktor.utils.ApiResponse
import com.shoppingbyktor.utils.AppConstants
import com.shoppingbyktor.utils.extension.apiResponse
import com.shoppingbyktor.utils.extension.currentUser
import com.shoppingbyktor.utils.extension.fileExtension
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.github.smiley4.ktoropenapi.put
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

/**
 * Route for managing user profile-related operations.
 *
 * @param userProfileController The controller responsible for handling user profile-related operations.
 */
fun Route.profileRoutes(userProfileController: ProfileController) {
    route("profile") {

        /**
         * GET request to retrieve the profile of the current user.
         *
         * @response A response containing the profile information of the user.
         */
        authenticate("jwt") {
            get({
                tags("Profile")
                summary = "auth[customer]"
                apiResponse()
            }) {
                call.respond(
                    ApiResponse.success(
                        userProfileController.getProfile(call.currentUser().userId), HttpStatusCode.OK
                    )
                )
            }
        }

        /**
         * PUT request to update the profile of the current user.
         *
         * @param firstName The user's first name.
         * @param lastName The user's last name.
         * @param mobile The user's secondary mobile number.
         * @param faxNumber The user's fax number.
         * @param streetAddress The user's street address.
         * @param city The user's city.
         * @param identificationType The type of identification provided.
         * @param identificationNo The identification number.
         * @param occupation The user's occupation.
         * @param postCode The user's postal code.
         * @param gender The user's gender.
         * @response A response indicating the success of the profile update.
         */
        put({
            tags("Profile")
            summary = "auth[customer]"
            request {
                queryParameter<String>("name")
                queryParameter<Int>("age")
            }
            apiResponse()
        }) {
            val params = UserProfileRequest(
                name = call.parameters["name"],
                age = call.parameters["age"]?.toIntOrNull(),
            )
            call.respond(
                ApiResponse.success(
                    userProfileController.updateProfile(call.currentUser().userId, params), HttpStatusCode.OK
                )
            )
        }

        /**
         * POST request to upload a new profile image for the user.
         *
         * @param image The image file to be uploaded.
         * @response A response containing the file name of the uploaded image.
         */
        post({
            tags("Profile")
            summary = "auth[customer]"
            request {
                multipartBody {
                    mediaTypes = setOf(ContentType.MultiPart.FormData)
                    part<File>("image") {
                        mediaTypes = setOf(
                            ContentType.Image.PNG, ContentType.Image.JPEG, ContentType.Image.SVG
                        )
                    }
                    part<String>("name")
                    part<String>("age")
                }
            }
            apiResponse()
        }) {
            val multipartData = call.receiveMultipart()
            var name: String? = null
            var age: Int? = null
            var imageFileNameInServer: String? = null

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        when (part.name) {
                            "name" -> name = part.value
                            "age" -> age = part.value.toIntOrNull()
                        }
                    }

                    is PartData.FileItem -> {
                        val fileName = part.originalFileName ?: "image"
                        val imageId = UUID.randomUUID()
                        val fileExtension = fileName.fileExtension()
                        val serverFileName = "$imageId$fileExtension"
                        val filePath = "${AppConstants.ImageFolder.PROFILE_IMAGE_LOCATION}$serverFileName"

                        withContext(Dispatchers.IO) {
                            File(filePath).writeBytes(part.streamProvider().readBytes())
                        }
                        imageFileNameInServer = serverFileName
                    }

                    else -> {}
                }
                part.dispose()
            }


            val userId = call.currentUser().userId
            val updatedProfile = userProfileController.updateProfileImage(userId, imageFileNameInServer, name, age)
            call.respond(ApiResponse.success(updatedProfile, HttpStatusCode.OK))
        }


    }
}