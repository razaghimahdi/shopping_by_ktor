package com.shoppingbyktor.database

import com.shoppingbyktor.database.entities.*
import com.shoppingbyktor.utils.Quintuple
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

internal fun seedProducts() = transaction {
    if (ProductTable.selectAll().empty()) {

        val config = HoconApplicationConfig(ConfigFactory.load("application.conf"))
        val port = config.property("ktor.deployment.port").getString().toInt()
        val host = config.property("ktor.deployment.host").getString()

        val productBaseImageUrl = "http://$host:$port/product-image/"
        val categoryBaseImageUrl = "http://$host:$port/category-image/"


        val banners = listOf(
            BannerDAO.new {
                banner = "https://d33wubrfki0l68.cloudfront.net/92837bc083afaee80fcf94e034868749daa829ea/5d5a0/images/blog/templarbit-illustration-csp-header-92837bc0.jpg"
            },
            BannerDAO.new {
                banner = "https://t4.ftcdn.net/jpg/03/06/69/49/360_F_306694930_S3Z8H9Qk1MN79ZUe7bEWqTFuonRZdemw.jpg"
            },
            BannerDAO.new {
                banner = "https://t3.ftcdn.net/jpg/04/38/59/88/360_F_438598896_D9pyLmbMZ02CrxURfHxU4nG5UlzXv6Dy.jpg"
            },
        )


        val categories = listOf(
            ProductCategoryDAO.new {
                name = "Computer"; icon = "${categoryBaseImageUrl}computer.png"
            },
            ProductCategoryDAO.new {
                name = "Electronics"; icon = "${categoryBaseImageUrl}electronics.png"
            },
            ProductCategoryDAO.new {
                name = "Arts & Crafts"; icon = "${categoryBaseImageUrl}arts.png"
            },
            ProductCategoryDAO.new {
                name = "Automotive"; icon = "${categoryBaseImageUrl}car.png"
            },
            ProductCategoryDAO.new {
                name = "Baby"; icon = "${categoryBaseImageUrl}baby_cloth.png"
            },
            ProductCategoryDAO.new {
                name = "Beauty and Personal Care"; icon = "${categoryBaseImageUrl}beauty.png"
            },
            ProductCategoryDAO.new {
                name = "Women's Fashion"; icon = "${categoryBaseImageUrl}women_cloth.png"
            },
            ProductCategoryDAO.new {
                name = "Men's Fashion"; icon = "${categoryBaseImageUrl}men_cloth.png"
            },
            ProductCategoryDAO.new {
                name = "Health and Household"; icon = "${categoryBaseImageUrl}health.png"
            },
            ProductCategoryDAO.new {
                name = "Home and Kitchen"; icon = "${categoryBaseImageUrl}kitchen.png"
            },
            ProductCategoryDAO.new {
                name = "Industrial and Scientific"; icon = "${categoryBaseImageUrl}science.png"
            },
            ProductCategoryDAO.new { name = "Luggage"; icon = "${categoryBaseImageUrl}camping.png" },
            ProductCategoryDAO.new {
                name = "Movies & Television"; icon = "${categoryBaseImageUrl}movies.png"
            },
            ProductCategoryDAO.new {
                name = "Pet Supplies"; icon = "${categoryBaseImageUrl}pet.png"
            },
            ProductCategoryDAO.new {
                name = "Sports and Outdoors"; icon = "${categoryBaseImageUrl}sports.png"
            },
            ProductCategoryDAO.new {
                name = "Tools & Home Improvement"; icon = "${categoryBaseImageUrl}tools.png"
            },
            ProductCategoryDAO.new {
                name = "Toys and Games"; icon = "${categoryBaseImageUrl}toys.png"
            }
        )

        val brands = listOf(
            // Electronics / Computer
            BrandDAO.new { name = "Sony"; logo = "https://1000logos.net/wp-content/uploads/2017/06/Sony-Logo.png" },
            BrandDAO.new {
                name = "Samsung"; logo = "https://1000logos.net/wp-content/uploads/2017/06/Samsung-Logo.png"
            },
            BrandDAO.new { name = "Apple"; logo = "https://1000logos.net/wp-content/uploads/2016/10/Apple-Logo.png" },
            BrandDAO.new { name = "HP"; logo = "https://1000logos.net/wp-content/uploads/2017/03/HP-Logo.png" },
            BrandDAO.new { name = "Dell"; logo = "https://1000logos.net/wp-content/uploads/2017/03/Dell-Logo.png" },

            // Clothing / Fashion
            BrandDAO.new { name = "Nike"; logo = "https://1000logos.net/wp-content/uploads/2017/03/Nike-Logo.png" },
            BrandDAO.new { name = "Adidas"; logo = "https://1000logos.net/wp-content/uploads/2017/05/Adidas-logo.png" },
            BrandDAO.new { name = "Zara"; logo = "https://1000logos.net/wp-content/uploads/2017/05/Zara-Logo.png" },
            BrandDAO.new { name = "H&M"; logo = "https://1000logos.net/wp-content/uploads/2017/05/HM-logo.png" },

            // Beauty and Personal Care
            BrandDAO.new {
                name = "L'Oréal"; logo = "https://1000logos.net/wp-content/uploads/2017/05/Loreal-logo.png"
            },
            BrandDAO.new {
                name = "Maybelline"; logo = "https://1000logos.net/wp-content/uploads/2022/05/Maybelline-logo.png"
            },

            // Baby
            BrandDAO.new {
                name = "Pampers"; logo = "https://1000logos.net/wp-content/uploads/2021/05/Pampers-logo.png"
            },
            BrandDAO.new {
                name = "Huggies"; logo = "https://1000logos.net/wp-content/uploads/2021/05/Huggies-logo.png"
            },

            // Home and Kitchen
            BrandDAO.new {
                name = "Philips"; logo = "https://1000logos.net/wp-content/uploads/2021/05/Philips-logo.png"
            },
            BrandDAO.new { name = "Tefal"; logo = "https://1000logos.net/wp-content/uploads/2021/08/Tefal-logo.png" },

            // Books
            BrandDAO.new {
                name = "Penguin Books"; logo =
                "https://1000logos.net/wp-content/uploads/2020/09/Penguin-Random-House-logo.png"
            },
            BrandDAO.new {
                name = "HarperCollins"; logo = "https://1000logos.net/wp-content/uploads/2020/08/HarperCollins-logo.png"
            },

            // Automotive
            BrandDAO.new { name = "Bosch"; logo = "https://1000logos.net/wp-content/uploads/2021/04/Bosch-logo.png" },
            BrandDAO.new {
                name = "Michelin"; logo = "https://1000logos.net/wp-content/uploads/2021/04/Michelin-logo.png"
            },

            // Tools & Home Improvement
            BrandDAO.new {
                name = "Black & Decker"; logo = "https://1000logos.net/wp-content/uploads/2021/05/Black-Decker-logo.png"
            },

            // Sports
            BrandDAO.new {
                name = "Decathlon"; logo = "https://1000logos.net/wp-content/uploads/2022/11/Decathlon-Logo.png"
            },

            // Pet Supplies
            BrandDAO.new { name = "Purina"; logo = "https://1000logos.net/wp-content/uploads/2022/03/Purina-logo.png" }
        )


        val sampleProducts = listOf(
            Quintuple(
                "Sony WH-1000XM5 Headphones",
                "The new integrated V1 processor unlocks the full potential of our QN1 HD Noise Cancelling processor to further enhance our (already) industry-leading noise cancelling technology.\n" +
                        "Precision engineered for exceptional high-resolution audio quality. Our precise voice recording technology with newly developed wind noise cancelling also ensures that you hear and be heard clearly when calling with these noise cancelling headphones.\n" +
                        "Smart, intuitive and intelligent. Adaptive Sound Control automatically adjusts sound settings to suit your environment and activity. Speak-to-chat automatically interrupts the music when you want to have a conversation without having to remove the over-ear headphones.\n" +
                        "Thanks to multi-point connection, the Bluetooth headphones can be paired with two devices at the same time. The Fast Pair function helps to search for headphones when they cannot be found. Swift pair supports pairing with a PC or tablet.\n" +
                        "With a battery life of up to 30 hours (when noise cancelling is on), you always have enough energy even on long journeys. These Sony headphones come with a folding case that makes storage and transport easier.\n" +
                        "Bluetooth specification version 5.2, effective range: 10 m.",
                299L,
                listOf("${productBaseImageUrl}sony1.jpg","${productBaseImageUrl}sony2.jpg","${productBaseImageUrl}sony3.jpg","${productBaseImageUrl}sony4.jpg","${productBaseImageUrl}sony5.jpg",).toString(),
                categories[0]
            ),
            Quintuple(
                "Samsung Galaxy S23", "Flagship smartphone with AMOLED display and 128GB storage.", 899L,
                listOf("${productBaseImageUrl}sumsuang1.jpg","${productBaseImageUrl}sumsuang2.jpg","${productBaseImageUrl}sumsuang3.jpg","${productBaseImageUrl}sumsuang4.jpg","${productBaseImageUrl}sumsuang5.jpg",).toString(),
                categories[1]
            ),
            Quintuple(
                "Nike Air Max", "Stylish and comfortable sneakers for everyday wear.", 129L,
                listOf("${productBaseImageUrl}nike1.jpg","${productBaseImageUrl}nike2.jpg","${productBaseImageUrl}nike3.jpg","${productBaseImageUrl}nike4.jpg","${productBaseImageUrl}nike5.jpg",).toString(),
                categories[7]
            ),
            Quintuple(
                "Adidas Running Shirt", "Breathable running shirt with sweat-wicking fabric.", 39L,
                listOf("${productBaseImageUrl}adidas1.jpg","${productBaseImageUrl}adidas2.jpg","${productBaseImageUrl}adidas3.jpg","${productBaseImageUrl}adidas4.jpg","${productBaseImageUrl}adidas5.jpg",).toString(),
                categories[6]
            ),
            Quintuple(
                "Digital Alarm Clock", "3-in-1 digital alarm clock radio: Glow is a digital alarm clock, FM radio and night light in one. The colour, brightness, alarm tone and volume of the night light can be adjusted individually. It is small and takes up little space on your bedside table.\n" +
                        "Cute rainbow LED numbers: the rainbow numbers make it look cute and fun. The numbers are large and easy to see even from the other side of the room. It also has a variable dial display dimmer that allows you to dim the numbers off or very bright.\n" +
                        "Night light and radio with auto-off timer: Glow offers a 7-colour night light with on/off option (5 levels adjustable). Plus a radio for bedtime music to enjoy a soothing and pleasant bedtime before bedtime. You can also set the sleep timer to automatically turn off both the night light and FM radio.\n" +
                        "Double alarm with 3 alarm sounds: Glow provides a double alarm that can be set at the same time and wakes you up with 3 sounds - your favourite radio station, built-in beep or birds. The volume of the alarm is adjustable and increases slowly so as not to scare you.\n" +
                        "Mains powered and battery backup: Glow is powered by the socket and can be secured with 3 x AAA batteries (battery is not included). It remains fully functional, the time is displayed and your alarm clock will still wake you up in the morning while it runs on batteries. Buy it and you will love it!", 19L,
                listOf("${productBaseImageUrl}clock1.jpg","${productBaseImageUrl}clock2.jpg","${productBaseImageUrl}clock3.jpg").toString(),
                categories[1]
            ),
            Quintuple(
                "Gaming Mouse", "63g Ultralight Design - No Empty Weight: More than 25% lighter than the Razer DeathAdder V2 Pro, you enjoy a level of speed and control appreciated by the best players in the world with one of the lightest ergonomic Esports mice ever developed.\n" +
                        "REFINED ERGONOMIC SHAPE - Optimized for unmatched handling and comfort: The iconic shape of the Razer DeathAdder has been developed in collaboration with top esports professionals and further enhanced to continue the legacy of award-winning handling and comfort.\n" +
                        "Razer Focus Pro 30K Optical Sensor - Premium precision: The Razer sensor provides flawless tracking performance on a variety of surfaces, including glass - supported by intelligent features for improved aiming accuracy and control.\n" +
                        "Razer Optical Mouse Switches Gen-3 - No problems with double clicks, no debounce delay: From an improved 90 million click lifespan with no double-click problems to a lightning-fast 0.2 ms actuation without debounce delay, the mouse has the reliability and speed designed for esports.\n" +
                        "Razer HyperSpeed Wireless - Extremely responsive multi-device connectivity: Experience a pristine connection that remains smooth and stable even in noisy wireless environments - Equipped with multi-device support for optimized esports setup.\n" +
                        "Up to 90 hours of battery life - no downtime when gaming: This wireless ergonomic esports mouse features a smaller, lighter battery with higher energy efficiency that allows up to 90 hours of continuous gaming and is rechargeable via USB Type C.", 59L,
                listOf("${productBaseImageUrl}mouse1.jpg","${productBaseImageUrl}mouse2.jpg","${productBaseImageUrl}mouse3.jpg","${productBaseImageUrl}mouse4.jpg","${productBaseImageUrl}mouse5.jpg",).toString(),
                categories[0]
            ),
        )

        repeat(30) { i ->
            val (title, desc, basePrice, images, categoryId) = sampleProducts.random()
            val brand = brands.random()
            val price = basePrice + (0..1000).random()
            val discount = if (i % 4 == 0) price * 0.85 else null


            ProductDAO.new {
                this.title = title
                description = desc
                this.categoryId = categoryId.id
                this.brandId = brand.id
                this.subCategoryId = null
                this.stockQuantity = (10..100).random()
                this.minOrderQuantity = 1
                this.soldCount = (0..500).random()
                this.price = price
                this.discountPrice = discount
                this.rate = (1..5).random().toDouble()
                this.videoLink = null
                this.hotDeal = i % 5 == 0
                this.featured = i % 6 == 0
                this.gallery = images
                this.status = ProductTable.ProductStatus.ACTIVE
            }
        }
    }
}
