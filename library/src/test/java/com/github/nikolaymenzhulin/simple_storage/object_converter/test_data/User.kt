/*
  Copyright © 2021 Nikolay Menzhulin.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package com.github.nikolaymenzhulin.simple_storage.object_converter.test_data

import java.io.Serializable

data class User(
        private val name: String = "Name",
        private val surname: String = "Surname"
) : Serializable

val expectedUser = User()

val expectedUserBytesGson =
        ByteArray(35).apply {
            set(0, 123)
            set(1, 34)
            set(2, 110)
            set(3, 97)
            set(4, 109)
            set(5, 101)
            set(6, 34)
            set(7, 58)
            set(8, 34)
            set(9, 78)
            set(10, 97)
            set(11, 109)
            set(12, 101)
            set(13, 34)
            set(14, 44)
            set(15, 34)
            set(16, 115)
            set(17, 117)
            set(18, 114)
            set(19, 110)
            set(20, 97)
            set(21, 109)
            set(22, 101)
            set(23, 34)
            set(24, 58)
            set(25, 34)
            set(26, 83)
            set(27, 117)
            set(28, 114)
            set(29, 110)
            set(30, 97)
            set(31, 109)
            set(32, 101)
            set(33, 34)
            set(34, 125)
        }

val expectedUserBytesSerializable =
        ByteArray(155).apply {
            set(0, -84)
            set(1, -19)
            set(2, 0)
            set(3, 5)
            set(4, 115)
            set(5, 114)
            set(6, 0)
            set(7, 74)
            set(8, 99)
            set(9, 111)
            set(10, 109)
            set(11, 46)
            set(12, 103)
            set(13, 105)
            set(14, 116)
            set(15, 104)
            set(16, 117)
            set(17, 98)
            set(18, 46)
            set(19, 110)
            set(20, 105)
            set(21, 107)
            set(22, 111)
            set(23, 108)
            set(24, 97)
            set(25, 121)
            set(26, 109)
            set(27, 101)
            set(28, 110)
            set(29, 122)
            set(30, 104)
            set(31, 117)
            set(32, 108)
            set(33, 105)
            set(34, 110)
            set(35, 46)
            set(36, 115)
            set(37, 105)
            set(38, 109)
            set(39, 112)
            set(40, 108)
            set(41, 101)
            set(42, 95)
            set(43, 115)
            set(44, 116)
            set(45, 111)
            set(46, 114)
            set(47, 97)
            set(48, 103)
            set(49, 101)
            set(50, 46)
            set(51, 111)
            set(52, 98)
            set(53, 106)
            set(54, 101)
            set(55, 99)
            set(56, 116)
            set(57, 95)
            set(58, 99)
            set(59, 111)
            set(60, 110)
            set(61, 118)
            set(62, 101)
            set(63, 114)
            set(64, 116)
            set(65, 101)
            set(66, 114)
            set(67, 46)
            set(68, 116)
            set(69, 101)
            set(70, 115)
            set(71, 116)
            set(72, 95)
            set(73, 100)
            set(74, 97)
            set(75, 116)
            set(76, 97)
            set(77, 46)
            set(78, 85)
            set(79, 115)
            set(80, 101)
            set(81, 114)
            set(82, -9)
            set(83, -2)
            set(84, 28)
            set(85, -69)
            set(86, -103)
            set(87, -19)
            set(88, -108)
            set(89, -113)
            set(90, 2)
            set(91, 0)
            set(92, 2)
            set(93, 76)
            set(94, 0)
            set(95, 4)
            set(96, 110)
            set(97, 97)
            set(98, 109)
            set(99, 101)
            set(100, 116)
            set(101, 0)
            set(102, 18)
            set(103, 76)
            set(104, 106)
            set(105, 97)
            set(106, 118)
            set(107, 97)
            set(108, 47)
            set(109, 108)
            set(110, 97)
            set(111, 110)
            set(112, 103)
            set(113, 47)
            set(114, 83)
            set(115, 116)
            set(116, 114)
            set(117, 105)
            set(118, 110)
            set(119, 103)
            set(120, 59)
            set(121, 76)
            set(122, 0)
            set(123, 7)
            set(124, 115)
            set(125, 117)
            set(126, 114)
            set(127, 110)
            set(128, 97)
            set(129, 109)
            set(130, 101)
            set(131, 113)
            set(132, 0)
            set(133, 126)
            set(134, 0)
            set(135, 1)
            set(136, 120)
            set(137, 112)
            set(138, 116)
            set(139, 0)
            set(140, 4)
            set(141, 78)
            set(142, 97)
            set(143, 109)
            set(144, 101)
            set(145, 116)
            set(146, 0)
            set(147, 7)
            set(148, 83)
            set(149, 117)
            set(150, 114)
            set(151, 110)
            set(152, 97)
            set(153, 109)
            set(154, 101)
        }