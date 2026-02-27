package com.vpnch.calmjournalapp.presentation.home.components

//@Composable
//fun RecordsList(
//    sections: List<RecordsSection>,
//    modifier: Modifier = Modifier
//) {
//    LazyColumn(
//        modifier = modifier,
//        contentPadding = PaddingValues(bottom = 24.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//// Заголовок "Последние записи"
//        item {
//            Text(
//                text = "Последние записи",
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
//            )
//        }
//
//        // Каждая секция
//        sections.forEach { section ->
//            // Подзаголовок секции
//            item {
//                Text(
//                    text = section.title,
//                    style = MaterialTheme.typography.labelLarge,
//                    color = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
//                )
//            }
//
//            section.items.forEach { record ->
//                item {
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 16.dp),
//                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//                    ) {
//                        Text(
//                            text = record.title,
//                            style = MaterialTheme.typography.bodyMedium,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}