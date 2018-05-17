# Copyright 2016 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""The command group for cloud container builds."""

from __future__ import absolute_import
from __future__ import unicode_literals
from googlecloudsdk.api_lib.cloudbuild import transforms
from googlecloudsdk.calliope import base


class Builds(base.Group):
  """Create and manage container builds."""

  @staticmethod
  def Args(parser):
    parser.display_info.AddTransforms(transforms.GetTransforms())